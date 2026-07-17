package com.bookmypro.provider_service.feature.serviceproviders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.provider_service.common.dto.ProviderDto;
import com.bookmypro.provider_service.common.dto.ReviewDto;
import com.bookmypro.provider_service.common.service.FileUploadService;
import com.bookmypro.provider_service.enums.ProviderStatus;
import com.bookmypro.provider_service.enums.ReviewStatus;
import com.bookmypro.provider_service.enums.Status;
import com.bookmypro.provider_service.enums.VerificationStatus;
import com.bookmypro.provider_service.exception.BusinessException;
import com.bookmypro.provider_service.exception.ErrorCode;
import com.bookmypro.provider_service.model.Provider;
import com.bookmypro.provider_service.model.ProviderAvailability;
import com.bookmypro.provider_service.model.ProviderDocument;
import com.bookmypro.provider_service.model.ProviderProfile;
import com.bookmypro.provider_service.model.ProviderService;
import com.bookmypro.provider_service.model.Review;
import com.bookmypro.provider_service.repository.ProviderAvailabilityRepository;
import com.bookmypro.provider_service.repository.ProviderDocumentRepository;
import com.bookmypro.provider_service.repository.ProviderProfileRepository;
import com.bookmypro.provider_service.repository.ProviderRepository;
import com.bookmypro.provider_service.repository.ProviderServiceRepository;
import com.bookmypro.provider_service.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceProviderService {

    private static final int MAX_PAGE_SIZE = 50;
    private static final DateTimeFormatter SLOT_TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
    private static final DateTimeFormatter REVIEW_DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private final ProviderServiceRepository providerServiceRepository;
    private final ProviderRepository providerRepository;
    private final ProviderProfileRepository providerProfileRepository;
    private final ProviderAvailabilityRepository providerAvailabilityRepository;
    private final ProviderDocumentRepository providerDocumentRepository;
    private final ReviewRepository reviewRepository;
    private final FileUploadService fileUploadService;

    @Transactional(readOnly = true)
    public List<ProviderDto> getServiceProviders(UUID serviceId, Integer pageNo, Integer pageSize) {
        validatePagination(pageNo, pageSize);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "minimumPrice"));

        Page<ProviderService> providerPage = providerServiceRepository
                .findByServiceIdAndStatusAndIsDeletedFalse(serviceId, Status.ACTIVE, pageable);

        if (providerPage.isEmpty()) {
            return List.of();
        }

        List<ProviderService> pageItems = providerPage.getContent();

        List<UUID> providerIds = pageItems.stream().map(ProviderService::getProviderId).distinct().toList();

        Map<UUID, Provider> providers = providerRepository.findByProviderIdIn(providerIds).stream()
                .collect(Collectors.toMap(Provider::getProviderId, Function.identity()));

        Map<UUID, ProviderProfile> profiles = providerProfileRepository.findByProviderIdIn(providerIds).stream()
                .collect(Collectors.toMap(ProviderProfile::getProviderId, Function.identity(), (a, b) -> a));

        Map<UUID, List<ProviderAvailability>> availabilityMap = providerAvailabilityRepository
                .findByProviderIdIn(providerIds).stream()
                .collect(Collectors.groupingBy(ProviderAvailability::getProviderId));

        Map<UUID, List<ProviderDocument>> documentMap = providerDocumentRepository.findByProviderIdIn(providerIds)
                .stream()
                .collect(Collectors.groupingBy(ProviderDocument::getProviderId));

        Map<UUID, List<Review>> reviewMap = reviewRepository
                .findByServiceIdAndProviderIdInAndStatus(serviceId, providerIds, ReviewStatus.PUBLISHED).stream()
                .collect(Collectors.groupingBy(Review::getProviderId));

        List<ProviderDto> response = new ArrayList<>();

        for (ProviderService providerService : pageItems) {
            Provider provider = providers.get(providerService.getProviderId());
            if (provider == null) {
                continue;
            }

            ProviderProfile profile = profiles.get(providerService.getProviderId());
            List<Review> reviews = reviewMap.getOrDefault(providerService.getProviderId(), List.of());

            response.add(ProviderDto.builder()
                    .providerId(provider.getProviderId().toString())
                    .fullName(buildFullName(provider))
                    .profileImage(resolveProfileImage(profile))
                    .averageRating(calculateAverageRating(reviews))
                    .totalReviews(reviews.size())
                    .experienceYears(resolveExperienceYears(providerService, profile))
                    .completedJobs(countCompletedJobs(reviews))
                    .distance(null)
                    .nextAvailableSlot(resolveNextSlot(availabilityMap.get(providerService.getProviderId())))
                    .minimumPrice(toInteger(providerService.getMinimumPrice()))
                    .verified(isVerified(provider, documentMap.get(providerService.getProviderId())))
                    .build());
        }

        log.info("Fetched {} providers for serviceId={}", response.size(), serviceId);
        return response;
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> getReviews(UUID serviceId, UUID providerId, Integer pageNo, Integer pageSize) {
        if (serviceId == null && providerId == null) {
            throw new BusinessException(ErrorCode.INVALID_REVIEW_FILTER);
        }

        validatePagination(pageNo, pageSize);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Review> reviewPage = reviewRepository.findPublishedReviews(
                serviceId,
                providerId,
                ReviewStatus.PUBLISHED,
                pageable);

        return reviewPage.getContent().stream()
                .map(this::toReviewDto)
                .toList();
    }

    private ReviewDto toReviewDto(Review review) {
        return ReviewDto.builder()
                .reviewId(review.getReviewId())
                .customerName("Customer")
                .customerImage("")
                .rating(review.getRating() != null ? review.getRating() : 0)
                .review(review.getReviewMessage())
                .reviewDate(review.getCreatedAt() != null
                        ? review.getCreatedAt().toLocalDate().format(REVIEW_DATE_FORMAT)
                        : null)
                .build();
    }

    private void validatePagination(Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo < 0) {
            throw new BusinessException(ErrorCode.INVALID_PAGE_NUMBER);
        }
        if (pageSize == null || pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            throw new BusinessException(ErrorCode.INVALID_PAGE_SIZE);
        }
    }

    private String buildFullName(Provider provider) {
        StringBuilder name = new StringBuilder();
        appendNamePart(name, provider.getFirstName());
        appendNamePart(name, provider.getMiddleName());
        appendNamePart(name, provider.getLastName());
        return name.isEmpty() ? "Professional" : name.toString();
    }

    private void appendNamePart(StringBuilder name, String part) {
        if (part == null || part.isBlank()) {
            return;
        }
        if (!name.isEmpty()) {
            name.append(' ');
        }
        name.append(part.trim());
    }

    private double calculateAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double average = reviews.stream()
                .map(Review::getRating)
                .filter(rating -> rating != null)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        return BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private int countCompletedJobs(List<Review> reviews) {
        long completed = reviews.stream()
                .map(Review::getBookingId)
                .filter(bookingId -> bookingId != null)
                .distinct()
                .count();
        return completed > 0 ? (int) completed : reviews.size();
    }

    private Integer resolveExperienceYears(ProviderService providerService, ProviderProfile profile) {
        if (providerService.getExperienceYears() != null) {
            return providerService.getExperienceYears();
        }
        if (profile != null && profile.getExperienceYears() != null) {
            return profile.getExperienceYears();
        }
        return 0;
    }

    private String resolveNextSlot(List<ProviderAvailability> availabilities) {
        if (availabilities == null || availabilities.isEmpty()) {
            return "Contact for availability";
        }

        DayOfWeek today = DayOfWeek.from(java.time.LocalDate.now());

        for (int offset = 0; offset < 7; offset++) {
            DayOfWeek targetDay = today.plus(offset);
            ProviderAvailability match = availabilities.stream()
                    .filter(slot -> Boolean.TRUE.equals(slot.getIsAvailable()))
                    .filter(slot -> targetDay.name().equalsIgnoreCase(slot.getDayOfWeek()))
                    .filter(slot -> slot.getStartTime() != null)
                    .min(Comparator.comparing(ProviderAvailability::getStartTime))
                    .orElse(null);

            if (match == null) {
                continue;
            }

            String dayLabel = offset == 0 ? "Today" : offset == 1 ? "Tomorrow"
                    : targetDay.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            return dayLabel + " " + match.getStartTime().format(SLOT_TIME_FORMAT);
        }

        return "Contact for availability";
    }

    private Integer toInteger(BigDecimal value) {
        if (value == null) {
            return 0;
        }
        return value.setScale(0, RoundingMode.HALF_UP).intValue();
    }

    private boolean isVerified(Provider provider, List<ProviderDocument> documents) {
        if (provider.getStatus() != ProviderStatus.ACTIVE || documents == null) {
            return false;
        }
        return documents.stream()
                .anyMatch(document -> document.getVerificationStatus() == VerificationStatus.VERIFIED);
    }

    private String resolveProfileImage(ProviderProfile profile) {
        if (profile == null || profile.getProfilePhotoUrl() == null || profile.getProfilePhotoUrl().isBlank()) {
            return null;
        }

        try {
            byte[] bytes = fileUploadService.getFile(profile.getProfilePhotoUrl());
            String mimeType = resolveMimeType(profile.getProfilePhotoUrl());
            return "data:" + mimeType + ";base64," + java.util.Base64.getEncoder().encodeToString(bytes);
        } catch (Exception ex) {
            log.warn("Failed to load profile image from path: {}", profile.getProfilePhotoUrl());
            return null;
        }
    }

    private String resolveMimeType(String filePath) {
        String lowercasePath = filePath.toLowerCase();
        if (lowercasePath.endsWith(".png")) {
            return "image/png";
        }
        if (lowercasePath.endsWith(".gif")) {
            return "image/gif";
        }
        if (lowercasePath.endsWith(".webp")) {
            return "image/webp";
        }
        return "image/jpeg";
    }
}
