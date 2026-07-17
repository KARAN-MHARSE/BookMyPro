package com.bookmypro.provider_service.feature.providerdetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bookmypro.provider_service.common.dto.AvailabilityDto;
import com.bookmypro.provider_service.common.dto.MasterDetailsDto;
import com.bookmypro.provider_service.common.dto.MasterDetailsRequest;
import com.bookmypro.provider_service.common.dto.ReviewDto;
import com.bookmypro.provider_service.common.service.downstream.MasterDownStreamService;
import com.bookmypro.provider_service.enums.LookupType;
import com.bookmypro.provider_service.enums.ReviewStatus;
import com.bookmypro.provider_service.exception.BusinessException;
import com.bookmypro.provider_service.exception.ErrorCode;
import com.bookmypro.provider_service.model.Provider;
import com.bookmypro.provider_service.model.ProviderAvailability;
import com.bookmypro.provider_service.model.ProviderProfile;
import com.bookmypro.provider_service.model.ProviderService;
import com.bookmypro.provider_service.model.Review;
import com.bookmypro.provider_service.repository.ProviderAvailabilityRepository;
import com.bookmypro.provider_service.repository.ProviderCertificationRepository;
import com.bookmypro.provider_service.repository.ProviderProfileRepository;
import com.bookmypro.provider_service.repository.ProviderRepository;
import com.bookmypro.provider_service.repository.ProviderServiceAreaRepository;
import com.bookmypro.provider_service.repository.ProviderServiceRepository;
import com.bookmypro.provider_service.repository.ProviderSkillRepository;
import com.bookmypro.provider_service.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProviderDetailsService {
	private final ProviderRepository providerRepository;
	private final ProviderProfileRepository providerProfileRepository;
	private final ProviderAvailabilityRepository providerAvailabilityRepository;
	private final ReviewRepository reviewRepository;
	private final ProviderSkillRepository providerSkillRepository;
	private final ProviderServiceRepository providerServiceRepository;
	private final ProviderCertificationRepository providerCertificationRepository;
	private final ProviderServiceAreaRepository providerServiceAreaRepository;
	private final MasterDownStreamService masterDownStreamService;

	public ProviderDetailsResponse fetchProviderDetails(UUID providerId) {
		if (providerId == null) {
			throw new BusinessException(ErrorCode.PROVIDER_NOT_FOUND);
		}
		
		ProviderDetailsResponse.ProviderDetailsResponseBuilder responseBuilder = new ProviderDetailsResponse.ProviderDetailsResponseBuilder();
		
		Provider provider = providerRepository.findById(providerId)
				.orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

		ProviderProfile profile = providerProfileRepository.findByProviderId(providerId)
				.orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));
		
		if (profile != null) {
			responseBuilder
				.id(providerId)
				.name(provider.getFirstName() + " " + provider.getLastName())
				.isVerified(true)
				.experienceYears(profile.getExperienceYears())
				.about(profile.getBio())
				.specialty(profile.getProfessionalTitle());
			
			String initials = "";
			if (provider.getFirstName() != null && !provider.getFirstName().isEmpty()) {
				initials += provider.getFirstName().substring(0, 1).toUpperCase();
			}
			if (provider.getLastName() != null && !provider.getLastName().isEmpty()) {
				initials += provider.getLastName().substring(0, 1).toUpperCase();
			}
			responseBuilder.initials(initials);
		}
		
		providerServiceAreaRepository.findByProviderId(providerId).ifPresent(area -> {
			String loc = "";
			if (area.getCity() != null) loc += area.getCity();
			if (area.getState() != null) {
				if (!loc.isEmpty()) loc += ", ";
				loc += area.getState();
			}
			responseBuilder.location(loc);
		});
		
		List<UUID> providerSkils = providerSkillRepository.findAllSkillIdsByProviderId(providerId);
		
		List<String> certifications = providerCertificationRepository.findAllCertificateNamesByProviderId(providerId);
		responseBuilder.certifications(certifications);
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<Review> reviewsPage = reviewRepository.findPublishedReviews(null, providerId, ReviewStatus.PUBLISHED, pageRequest);
		
		List<ReviewDto> reviews = reviewsPage.getContent().stream()
				.filter(Objects::nonNull)
				.map(review -> ReviewDto.builder()
						.reviewId(review.getReviewId())
						.rating(review.getRating())
						.review(review.getReviewMessage() != null ? review.getReviewMessage() : review.getReviewTitle())
						.reviewDate(review.getCreatedAt().toString())
						.customerName(review.getCreatedBy() != null ? review.getCreatedBy() : "Client")
						.build())
				.collect(Collectors.toList());
		
		responseBuilder.reviews(reviews);
		
		double averageRating = 0.0;
		int reviewCount = reviews.size();
		if (reviewCount > 0) {
			averageRating = reviews.stream()
					.mapToDouble(ReviewDto::getRating)
					.average()
					.orElse(0.0);
			averageRating = Math.round(averageRating * 10.0) / 10.0;
		}
		responseBuilder.rating(averageRating);
		responseBuilder.reviewCount(reviewCount);
		responseBuilder.isTopRated(averageRating >= 4.5);
		responseBuilder.completedJobs(profile.getExperienceYears() != null ? profile.getExperienceYears() * 85 : 50);
		responseBuilder.responseTime("Within an hour");
		responseBuilder.portfolioGradients(List.of(
			"linear-gradient(135deg, #DBEAFE, #BFDBFE)",
			"linear-gradient(135deg, #FEF3C7, #FDE68A)",
			"linear-gradient(135deg, #FCE7F3, #FBCFE8)",
			"linear-gradient(135deg, #D1FAE5, #A7F3D0)"
		));
		
		List<ProviderAvailability> availabilties = providerAvailabilityRepository.findByProviderId(providerId);
		
		List<AvailabilityDto> availabilityDto = availabilties.stream()
				.map(a -> AvailabilityDto.builder()
						.availabilityId(a.getAvailabilityId())
						.dayOfWeek(a.getDayOfWeek())
						.startTime(a.getStartTime())
						.endTime(a.getEndTime())
						.isAvailable(a.getIsAvailable())
						.build())
				.toList();
		
		responseBuilder.availability(availabilityDto);
		
		String workingHours = availabilties.stream()
				.filter(a -> a != null && a.getIsAvailable() != null && a.getIsAvailable())
				.findFirst()
				.map(a -> a.getStartTime() + " - " + a.getEndTime())
				.orElse("09:00 AM - 06:00 PM");
		responseBuilder.workingHours(workingHours);
		
		List<ProviderService> providerServices = providerServiceRepository.findByProviderIdAndIsDeletedFalse(providerId);
		
		int startingPrice = providerServices.stream()
				.map(ps -> ps.getMinimumPrice() != null ? ps.getMinimumPrice() : (ps.getBasePrice() != null ? ps.getBasePrice() : BigDecimal.ZERO))
				.filter(price -> price.compareTo(BigDecimal.ZERO) > 0)
				.map(BigDecimal::intValue)
				.min(Integer::compare)
				.orElse(25);
		
		responseBuilder.startingPrice(startingPrice);
		
		// Downstream call to master-service using masterDownStreamService
		Map<LookupType, List<UUID>> query = new HashMap<>();
		if (providerSkils != null && !providerSkils.isEmpty()) {
			query.put(LookupType.SKILL, providerSkils);
		}
		List<UUID> serviceIds = providerServices.stream()
				.map(ProviderService::getServiceId)
				.filter(Objects::nonNull)
				.toList();
		if (!serviceIds.isEmpty()) {
			query.put(LookupType.SERVICE, serviceIds);
		}
		
		Map<LookupType, List<MasterDetailsDto>> masterDetails = new HashMap<>();
		if (!query.isEmpty()) {
			try {
				var response = masterDownStreamService.getMasterDetails(new MasterDetailsRequest(query));
				if (response != null && response.getBody() != null) {
					masterDetails = response.getBody();
				}
			} catch (Exception e) {
				log.error("Failed to fetch details from master-service", e);
			}
		}
		
		List<MasterDetailsDto> skillList = masterDetails.get(LookupType.SKILL);
		List<String> skillsNames = skillList != null 
				? skillList.stream().map(MasterDetailsDto::getDesc).toList() 
				: new ArrayList<>();
		responseBuilder.skills(skillsNames);
		
		List<MasterDetailsDto> serviceNamesList = masterDetails.get(LookupType.SERVICE);
		Map<UUID, String> serviceNamesMap = serviceNamesList != null 
				? serviceNamesList.stream().collect(Collectors.toMap(MasterDetailsDto::getMasterId, MasterDetailsDto::getDesc, (a, b) -> a)) 
				: new HashMap<>();
		
		List<ServiceOfferedDto> servicesOffered = providerServices.stream()
				.map(ps -> {
					String title = serviceNamesMap.getOrDefault(ps.getServiceId(), "Service");
					String duration = ps.getEstimatedDuration() != null ? ps.getEstimatedDuration() + " mins" : "N/A";
					int price = ps.getMinimumPrice() != null ? ps.getMinimumPrice().intValue() : (ps.getBasePrice() != null ? ps.getBasePrice().intValue() : 0);
					return ServiceOfferedDto.builder()
							.title(title)
							.duration(duration)
							.price(price)
							.includesSupplies(false)
							.build();
				})
				.toList();
		responseBuilder.servicesOffered(servicesOffered);
		
		return responseBuilder.build();
	}
}
