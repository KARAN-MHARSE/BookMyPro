package com.bookmypro.provider_service.feature.profile.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.provider_service.common.dto.LookupCriteria;
import com.bookmypro.provider_service.common.dto.LookupDto;
import com.bookmypro.provider_service.common.request.LookupRequest;
import com.bookmypro.provider_service.common.service.downstream.MasterDownStreamService;
import com.bookmypro.provider_service.enums.LookupType;
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
    private final MasterDownStreamService masterDownStreamService;

    public ServiceResponse getServicesByCredentialId(UUID credentialId) {
        log.info("Fetching service offerings for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        List<ProviderService> servicesList = providerServiceRepository.findByProviderIdAndIsDeletedFalse(provider.getProviderId());
        
        LookupRequest request = new LookupRequest();
        LookupCriteria criteria1 = new LookupCriteria(LookupType.SERVICE,null);
        LookupCriteria criteria2 = new LookupCriteria(LookupType.SERVICE_CATEGORY,null);
        request.setLookups(List.of(criteria1,criteria2));
        ResponseEntity<Map<String, List<LookupDto>>> lookupRes =masterDownStreamService.getLookups(request);
        
        Map<String, List<LookupDto>> looukups = Optional.ofNullable(lookupRes.getBody())
        		.orElse(new HashMap<>());
        
        ServiceResponse response= new ServiceResponse();
        response.setLookups(looukups);

        List<ServiceDto> services = servicesList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        response.setServices(services);
        return response;
        
    }

    @Transactional
    public ServiceDto saveOrUpdate(ServiceRequest request) {
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

    private ServiceDto mapToResponse(ProviderService s) {
        return ServiceDto.builder()
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
