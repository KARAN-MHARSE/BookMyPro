package com.bookmypro.provider_service.feature.profile.experience;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.provider_service.exception.BusinessException;
import com.bookmypro.provider_service.exception.ErrorCode;
import com.bookmypro.provider_service.model.Provider;
import com.bookmypro.provider_service.model.ProviderExperience;
import com.bookmypro.provider_service.repository.ProviderExperienceRepository;
import com.bookmypro.provider_service.repository.ProviderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExperienceService {

    private final ProviderRepository providerRepository;
    private final ProviderExperienceRepository providerExperienceRepository;

    public List<ExperienceResponse> getExperiencesByCredentialId(UUID credentialId) {
        log.info("Fetching experiences for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        List<ProviderExperience> experiences = providerExperienceRepository.findByProviderIdAndIsDeletedFalse(provider.getProviderId());

        return experiences.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExperienceResponse saveOrUpdate(ExperienceRequest request) {
        log.info("Saving or updating experience record, id={}", request.getExperienceId());

        Provider provider = providerRepository.findByCredentialId(request.getCredentialId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        ProviderExperience experience;
        if (request.getExperienceId() != null) {
            experience = providerExperienceRepository.findById(request.getExperienceId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_PROFILE_NOT_FOUND));
        } else {
            experience = ProviderExperience.builder()
                    .providerId(provider.getProviderId())
                    .createdAt(LocalDateTime.now())
                    .isDeleted(false)
                    .build();
        }

        experience.setCompanyName(request.getCompanyName());
        experience.setDesignation(request.getDesignation());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(Boolean.TRUE.equals(request.getCurrentlyWorking()) ? null : request.getEndDate());
        experience.setCurrentlyWorking(request.getCurrentlyWorking());
        experience.setDescription(request.getDescription());
        experience.setUpdatedAt(LocalDateTime.now());

        ProviderExperience saved = providerExperienceRepository.save(experience);
        return mapToResponse(saved);
    }

    @Transactional
    public void deleteExperience(UUID experienceId) {
        log.info("Soft deleting experience record with id={}", experienceId);
        ProviderExperience experience = providerExperienceRepository.findById(experienceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_PROFILE_NOT_FOUND));
        experience.setIsDeleted(true);
        experience.setUpdatedAt(LocalDateTime.now());
        providerExperienceRepository.save(experience);
    }

    private ExperienceResponse mapToResponse(ProviderExperience exp) {
        return ExperienceResponse.builder()
                .experienceId(exp.getExperienceId())
                .providerId(exp.getProviderId())
                .companyName(exp.getCompanyName())
                .designation(exp.getDesignation())
                .startDate(exp.getStartDate())
                .endDate(exp.getEndDate())
                .currentlyWorking(exp.getCurrentlyWorking())
                .description(exp.getDescription())
                .build();
    }
}
