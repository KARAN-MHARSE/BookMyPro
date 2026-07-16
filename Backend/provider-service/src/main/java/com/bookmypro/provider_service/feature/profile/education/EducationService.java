package com.bookmypro.provider_service.feature.profile.education;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.provider_service.exception.BusinessException;
import com.bookmypro.provider_service.exception.ErrorCode;
import com.bookmypro.provider_service.model.Provider;
import com.bookmypro.provider_service.model.ProviderEducation;
import com.bookmypro.provider_service.repository.ProviderEducationRepository;
import com.bookmypro.provider_service.repository.ProviderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationService {

    private final ProviderRepository providerRepository;
    private final ProviderEducationRepository providerEducationRepository;

    public List<EducationResponse> getEducationsByCredentialId(UUID credentialId) {
        log.info("Fetching educations for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        List<ProviderEducation> educations = providerEducationRepository.findByProviderIdAndIsDeletedFalse(provider.getProviderId());

        return educations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EducationResponse saveOrUpdate(EducationRequest request) {
        log.info("Saving or updating education record, id={}", request.getEducationId());

        Provider provider = providerRepository.findByCredentialId(request.getCredentialId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        ProviderEducation education;
        if (request.getEducationId() != null) {
            education = providerEducationRepository.findById(request.getEducationId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_PROFILE_NOT_FOUND));
        } else {
            education = ProviderEducation.builder()
                    .providerId(provider.getProviderId())
                    .createdAt(LocalDateTime.now())
                    .isDeleted(false)
                    .build();
        }

        education.setInstitutionName(request.getInstitutionName());
        education.setDegree(request.getDegree());
        education.setSpecialization(request.getSpecialization());
        education.setStartYear(request.getStartYear());
        education.setEndYear(request.getEndYear());
        education.setCurrentlyStudying(request.getCurrentlyStudying());
        education.setDescription(request.getDescription());
        education.setUpdatedAt(LocalDateTime.now());

        ProviderEducation saved = providerEducationRepository.save(education);
        return mapToResponse(saved);
    }

    @Transactional
    public void deleteEducation(UUID educationId) {
        log.info("Soft deleting education record with id={}", educationId);
        ProviderEducation education = providerEducationRepository.findById(educationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_PROFILE_NOT_FOUND));
        education.setIsDeleted(true);
        education.setUpdatedAt(LocalDateTime.now());
        providerEducationRepository.save(education);
    }

    private EducationResponse mapToResponse(ProviderEducation education) {
        return EducationResponse.builder()
                .educationId(education.getEducationId())
                .providerId(education.getProviderId())
                .institutionName(education.getInstitutionName())
                .degree(education.getDegree())
                .specialization(education.getSpecialization())
                .startYear(education.getStartYear())
                .endYear(education.getEndYear())
                .currentlyStudying(education.getCurrentlyStudying())
                .description(education.getDescription())
                .build();
    }
}
