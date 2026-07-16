package com.bookmypro.provider_service.feature.profile.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.provider_service.enums.PriceType;
import com.bookmypro.provider_service.enums.Status;
import com.bookmypro.provider_service.exception.BusinessException;
import com.bookmypro.provider_service.exception.ErrorCode;
import com.bookmypro.provider_service.model.Provider;
import com.bookmypro.provider_service.model.ProviderService;
import com.bookmypro.provider_service.repository.ProviderRepository;
import com.bookmypro.provider_service.repository.ProviderServiceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProviderServicesService {

    private final ProviderRepository providerRepository;
    private final ProviderServiceRepository providerServiceRepository;

    public List<ServiceResponse> getServicesByCredentialId(UUID credentialId) {
        log.info("Fetching service offerings for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        List<ProviderService> servicesList = providerServiceRepository.findByProviderIdAndIsDeletedFalse(provider.getProviderId());

        return servicesList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ServiceResponse saveOrUpdate(ServiceRequest request) {
        log.info("Saving or updating service offering, id={}", request.getProviderServiceId());

        Provider provider = providerRepository.findByCredentialId(request.getCredentialId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        ProviderService serviceEntry;
        if (request.getProviderServiceId() != null) {
            serviceEntry = providerServiceRepository.findById(request.getProviderServiceId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_PROFILE_NOT_FOUND));
        } else {
            serviceEntry = ProviderService.builder()
                    .providerId(provider.getProviderId())
                    .createdAt(LocalDateTime.now())
                    .isDeleted(false)
                    .build();
        }

        serviceEntry.setServiceId(request.getServiceId());
        serviceEntry.setCategoryId(request.getCategoryId());
        serviceEntry.setExperienceYears(request.getExperienceYears());
        serviceEntry.setBasePrice(request.getBasePrice());
        serviceEntry.setMinimumPrice(request.getMinimumPrice());
        serviceEntry.setMaximumPrice(request.getMaximumPrice());
        serviceEntry.setEstimatedDuration(request.getEstimatedDuration());
        serviceEntry.setHomeService(Boolean.TRUE.equals(request.getHomeService()));
        serviceEntry.setEmergencyService(Boolean.TRUE.equals(request.getEmergencyService()));
        serviceEntry.setWeekendAvailable(Boolean.TRUE.equals(request.getWeekendAvailable()));
        serviceEntry.setUpdatedAt(LocalDateTime.now());

        if (request.getPriceType() != null) {
            try {
                serviceEntry.setPriceType(PriceType.valueOf(request.getPriceType().toUpperCase().trim()));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid price type supplied: {}", request.getPriceType());
            }
        }

        if (Boolean.TRUE.equals(request.getActive())) {
            serviceEntry.setStatus(Status.ACTIVE);
        } else {
            serviceEntry.setStatus(Status.INACTIVE);
        }

        ProviderService saved = providerServiceRepository.save(serviceEntry);
        return mapToResponse(saved);
    }

    @Transactional
    public void deleteService(UUID providerServiceId) {
        log.info("Soft deleting provider service offering, id={}", providerServiceId);
        ProviderService serviceEntry = providerServiceRepository.findById(providerServiceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_PROFILE_NOT_FOUND));
        serviceEntry.setIsDeleted(true);
        serviceEntry.setStatus(Status.INACTIVE);
        serviceEntry.setUpdatedAt(LocalDateTime.now());
        providerServiceRepository.save(serviceEntry);
    }

    private ServiceResponse mapToResponse(ProviderService s) {
        return ServiceResponse.builder()
                .providerServiceId(s.getProviderServiceId())
                .providerId(s.getProviderId())
                .serviceId(s.getServiceId())
                .categoryId(s.getCategoryId())
                .experienceYears(s.getExperienceYears())
                .basePrice(s.getBasePrice())
                .priceType(s.getPriceType() != null ? s.getPriceType().name() : null)
                .minimumPrice(s.getMinimumPrice())
                .maximumPrice(s.getMaximumPrice())
                .estimatedDuration(s.getEstimatedDuration())
                .homeService(s.getHomeService())
                .emergencyService(s.getEmergencyService())
                .weekendAvailable(s.getWeekendAvailable())
                .status(s.getStatus() != null ? s.getStatus().name() : null)
                .active(s.getStatus() == Status.ACTIVE)
                .build();
    }
}
