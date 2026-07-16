package com.bookmypro.provider_service.feature.profile.availability;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.provider_service.exception.BusinessException;
import com.bookmypro.provider_service.exception.ErrorCode;
import com.bookmypro.provider_service.model.Provider;
import com.bookmypro.provider_service.model.ProviderAvailability;
import com.bookmypro.provider_service.model.ProviderBreak;
import com.bookmypro.provider_service.model.ProviderProfile;
import com.bookmypro.provider_service.repository.ProviderAvailabilityRepository;
import com.bookmypro.provider_service.repository.ProviderBreakRepository;
import com.bookmypro.provider_service.repository.ProviderProfileRepository;
import com.bookmypro.provider_service.repository.ProviderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvailabilityService {

    private final ProviderRepository providerRepository;
    private final ProviderProfileRepository providerProfileRepository;
    private final ProviderAvailabilityRepository providerAvailabilityRepository;
    private final ProviderBreakRepository providerBreakRepository;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public AvailabilityResponse getAvailabilityByCredentialId(UUID credentialId) {
        log.info("Fetching availability settings for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        Optional<ProviderProfile> profileOpt = providerProfileRepository.findByProviderId(provider.getProviderId());

        List<ProviderAvailability> availabilities = providerAvailabilityRepository.findByProviderId(provider.getProviderId());

        List<String> workingDays = availabilities.stream()
                .map(ProviderAvailability::getDayOfWeek)
                .collect(Collectors.toList());

        String startTime = "09:00";
        String endTime = "18:00";
        String breakStart = "13:00";
        String breakEnd = "14:00";

        if (!availabilities.isEmpty()) {
            ProviderAvailability firstAvail = availabilities.get(0);
            if (firstAvail.getStartTime() != null) {
                startTime = firstAvail.getStartTime().format(TIME_FORMATTER);
            }
            if (firstAvail.getEndTime() != null) {
                endTime = firstAvail.getEndTime().format(TIME_FORMATTER);
            }

            Optional<ProviderBreak> pBreakOpt = providerBreakRepository.findByAvailabilityId(firstAvail.getAvailabilityId());
            if (pBreakOpt.isPresent()) {
                ProviderBreak pBreak = pBreakOpt.get();
                if (pBreak.getStartTime() != null) {
                    breakStart = pBreak.getStartTime().format(TIME_FORMATTER);
                }
                if (pBreak.getEndTime() != null) {
                    breakEnd = pBreak.getEndTime().format(TIME_FORMATTER);
                }
            }
        }

        AvailabilityResponse.AvailabilityResponseBuilder builder = AvailabilityResponse.builder()
                .workingDays(workingDays)
                .startTime(startTime)
                .endTime(endTime)
                .breakStart(breakStart)
                .breakEnd(breakEnd);

        if (profileOpt.isPresent()) {
            ProviderProfile profile = profileOpt.get();
            builder.serviceRadius(profile.getServiceRadius())
                   .maxBookings(profile.getMaxBookings())
                   .homeService(profile.getHomeService())
                   .emergencyService(profile.getEmergencyService())
                   .weekendAvailable(profile.getWeekendAvailable());
        } else {
            builder.serviceRadius(15)
                   .maxBookings(8)
                   .homeService(true)
                   .emergencyService(false)
                   .weekendAvailable(false);
        }

        return builder.build();
    }

    @Transactional
    public AvailabilityResponse saveOrUpdate(UUID credentialId, AvailabilityRequest request) {
        log.info("Saving / updating availability settings for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        ProviderProfile profile = providerProfileRepository.findByProviderId(provider.getProviderId())
                .orElseGet(() -> ProviderProfile.builder()
                        .providerId(provider.getProviderId())
                        .createdAt(LocalDateTime.now())
                        .build());

        profile.setServiceRadius(request.getServiceRadius());
        profile.setMaxBookings(request.getMaxBookings());
        profile.setHomeService(request.getHomeService());
        profile.setEmergencyService(request.getEmergencyService());
        profile.setWeekendAvailable(request.getWeekendAvailable());
        profile.setUpdatedAt(LocalDateTime.now());
        providerProfileRepository.save(profile);

        // 2. Clear old availability list records using bulk deletes to avoid N+1 queries
        List<ProviderAvailability> existingAvail = providerAvailabilityRepository.findByProviderId(provider.getProviderId());
        if (!existingAvail.isEmpty()) {
            List<UUID> availabilityIds = existingAvail.stream()
                    .map(ProviderAvailability::getAvailabilityId)
                    .collect(Collectors.toList());
            providerBreakRepository.deleteByAvailabilityIdIn(availabilityIds);
            providerAvailabilityRepository.deleteByProviderId(provider.getProviderId());
        }

        // 3. Persist new schedules
        List<String> days = request.getWorkingDays() != null ? request.getWorkingDays() : new ArrayList<>();
        LocalTime start = request.getStartTime() != null ? LocalTime.parse(request.getStartTime()) : LocalTime.of(9, 0);
        LocalTime end = request.getEndTime() != null ? LocalTime.parse(request.getEndTime()) : LocalTime.of(18, 0);

        for (String day : days) {
            ProviderAvailability avail = ProviderAvailability.builder()
                    .providerId(provider.getProviderId())
                    .dayOfWeek(day.toUpperCase().trim())
                    .startTime(start)
                    .endTime(end)
                    .isAvailable(true)
                    .createdAt(LocalDateTime.now())
                    .build();
            ProviderAvailability savedAvail = providerAvailabilityRepository.save(avail);

            if (request.getBreakStart() != null && request.getBreakEnd() != null) {
                ProviderBreak pBreak = ProviderBreak.builder()
                        .availabilityId(savedAvail.getAvailabilityId())
                        .startTime(LocalTime.parse(request.getBreakStart()))
                        .endTime(LocalTime.parse(request.getBreakEnd()))
                        .build();
                providerBreakRepository.save(pBreak);
            }
        }

        return AvailabilityResponse.builder()
                .workingDays(days)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .breakStart(request.getBreakStart())
                .breakEnd(request.getBreakEnd())
                .serviceRadius(request.getServiceRadius())
                .maxBookings(request.getMaxBookings())
                .homeService(request.getHomeService())
                .emergencyService(request.getEmergencyService())
                .weekendAvailable(request.getWeekendAvailable())
                .build();
    }
}
