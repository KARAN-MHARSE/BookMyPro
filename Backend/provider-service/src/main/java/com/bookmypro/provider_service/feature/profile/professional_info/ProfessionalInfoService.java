package com.bookmypro.provider_service.feature.profile.professional_info;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.provider_service.enums.Status;
import com.bookmypro.provider_service.exception.BusinessException;
import com.bookmypro.provider_service.exception.ErrorCode;
import com.bookmypro.provider_service.model.Provider;
import com.bookmypro.provider_service.model.ProviderBusiness;
import com.bookmypro.provider_service.model.ProviderProfile;
import com.bookmypro.provider_service.repository.ProviderBusinessRepository;
import com.bookmypro.provider_service.repository.ProviderProfileRepository;
import com.bookmypro.provider_service.repository.ProviderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfessionalInfoService {

    private final ProviderRepository providerRepository;
    private final ProviderProfileRepository providerProfileRepository;
    private final ProviderBusinessRepository providerBusinessRepository;

    public ProfessionalInfoResponse getProfessionalInfoByCredentialId(UUID credentialId) {
        log.info("Fetching provider professional info for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        Optional<ProviderProfile> profileOpt = providerProfileRepository.findByProviderId(provider.getProviderId());
        Optional<ProviderBusiness> businessOpt = providerBusinessRepository.findByProviderId(provider.getProviderId());

        ProfessionalInfoResponse.ProfessionalInfoResponseBuilder builder = ProfessionalInfoResponse.builder()
                .providerId(provider.getProviderId())
                .credentialId(provider.getCredentialId());

        if (profileOpt.isPresent()) {
            ProviderProfile profile = profileOpt.get();
            builder.professionalTitle(profile.getProfessionalTitle())
                   .providerType(profile.getProviderType())
                   .experience(profile.getExperienceYears())
                   .professionalSummary(profile.getProfessionalSummary());
        }

        if (businessOpt.isPresent()) {
            ProviderBusiness business = businessOpt.get();
            builder.businessName(business.getBusinessName())
                   .workingSince(business.getWorkingSince())
                   .gstNumber(business.getGstNumber())
                   .panNumber(business.getPanNumber())
                   .website(business.getWebsiteUrl())
                   .portfolio(business.getPortfolioUrl());
        }

        return builder.build();
    }

    @Transactional
    public ProfessionalInfoResponse saveOrUpdate(UUID credentialId, ProfessionalInfoRequest request) {
        log.info("Saving or updating provider professional info for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        // 1. Load or create ProviderProfile details
        ProviderProfile profile = providerProfileRepository.findByProviderId(provider.getProviderId())
                .orElseGet(() -> ProviderProfile.builder()
                        .providerId(provider.getProviderId())
                        .profileStatus(Status.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .build());

        profile.setProfessionalTitle(request.getProfessionalTitle());
        profile.setProviderType(request.getProviderType());
        profile.setExperienceYears(request.getExperience());
        profile.setProfessionalSummary(request.getProfessionalSummary());
        profile.setUpdatedAt(LocalDateTime.now());
        providerProfileRepository.save(profile);

        // 2. Load or create ProviderBusiness details
        ProviderBusiness business = providerBusinessRepository.findByProviderId(provider.getProviderId())
                .orElseGet(() -> ProviderBusiness.builder()
                        .providerId(provider.getProviderId())
                        .createdAt(LocalDateTime.now())
                        .build());

        business.setBusinessName(request.getBusinessName());
        business.setWorkingSince(request.getWorkingSince());
        business.setGstNumber(request.getGstNumber());
        business.setPanNumber(request.getPanNumber());
        business.setWebsiteUrl(request.getWebsite());
        business.setPortfolioUrl(request.getPortfolio());
        business.setUpdatedAt(LocalDateTime.now());
        providerBusinessRepository.save(business);

        return ProfessionalInfoResponse.builder()
                .providerId(provider.getProviderId())
                .credentialId(provider.getCredentialId())
                .professionalTitle(profile.getProfessionalTitle())
                .providerType(profile.getProviderType())
                .experience(profile.getExperienceYears())
                .professionalSummary(profile.getProfessionalSummary())
                .businessName(business.getBusinessName())
                .workingSince(business.getWorkingSince())
                .gstNumber(business.getGstNumber())
                .panNumber(business.getPanNumber())
                .website(business.getWebsiteUrl())
                .portfolio(business.getPortfolioUrl())
                .build();
    }
}
