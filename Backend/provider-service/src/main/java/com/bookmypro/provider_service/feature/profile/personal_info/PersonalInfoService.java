package com.bookmypro.provider_service.feature.profile.personal_info;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.provider_service.enums.Gender;
import com.bookmypro.provider_service.enums.Status;
import com.bookmypro.provider_service.exception.BusinessException;
import com.bookmypro.provider_service.exception.ErrorCode;
import com.bookmypro.provider_service.model.Provider;
import com.bookmypro.provider_service.model.ProviderLanguage;
import com.bookmypro.provider_service.model.ProviderProfile;
import com.bookmypro.provider_service.repository.ProviderLanguageRepository;
import com.bookmypro.provider_service.repository.ProviderProfileRepository;
import com.bookmypro.provider_service.repository.ProviderRepository;

import com.bookmypro.provider_service.common.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalInfoService {

    private final ProviderRepository providerRepository;
    private final ProviderProfileRepository providerProfileRepository;
    private final ProviderLanguageRepository providerLanguageRepository;
    private final FileUploadService fileUploadService;

    public PersonalInfoResponse getProfileByCredentialId(UUID credentialId) {
        log.info("Fetching provider profile personal info for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        Optional<ProviderProfile> profileOpt = providerProfileRepository.findByProviderId(provider.getProviderId());

        List<ProviderLanguage> languages = providerLanguageRepository.findByProviderId(provider.getProviderId());
        String languageStr = languages.isEmpty() ? null : languages.get(0).getLanguage();

        PersonalInfoResponse.PersonalInfoResponseBuilder builder = PersonalInfoResponse.builder()
                .providerId(provider.getProviderId())
                .credentialId(provider.getCredentialId())
                .firstName(provider.getFirstName())
                .middleName(provider.getMiddleName())
                .lastName(provider.getLastName())
                .phoneNumber(provider.getPhoneNumber())
                .language(languageStr);

        if (profileOpt.isPresent()) {
            ProviderProfile profile = profileOpt.get();
            String profilePhotoBase64 = null;
            if (profile.getProfilePhotoUrl() != null && !profile.getProfilePhotoUrl().isBlank()) {
                try {
                    byte[] bytes = fileUploadService.getFile(profile.getProfilePhotoUrl());
                    String mimeType = "image/jpeg";
                    String lowercasePath = profile.getProfilePhotoUrl().toLowerCase();
                    if (lowercasePath.endsWith(".png")) {
                        mimeType = "image/png";
                    } else if (lowercasePath.endsWith(".gif")) {
                        mimeType = "image/gif";
                    } else if (lowercasePath.endsWith(".webp")) {
                        mimeType = "image/webp";
                    }
                    profilePhotoBase64 = "data:" + mimeType + ";base64," + java.util.Base64.getEncoder().encodeToString(bytes);
                } catch (Exception e) {
                    log.warn("Failed to load profile photo file from path: {}", profile.getProfilePhotoUrl());
                }
            }

            builder.profilePhoto(profilePhotoBase64)
                   .bio(profile.getBio())
                   .gender(profile.getGender() != null ? profile.getGender().name() : null)
                   .dob(profile.getDob() != null ? profile.getDob().toString() : null);
        }

        return builder.build();
    }

    @Transactional
    public PersonalInfoResponse saveOrUpdate(UUID credentialId, ProfileRequest request) {
        log.info("Saving or updating provider profile personal info for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        provider.setFirstName(request.getFirstName());
        provider.setMiddleName(request.getMiddleName());
        provider.setLastName(request.getLastName());
        provider.setPhoneNumber(request.getPhoneNumber());
        providerRepository.save(provider);

        ProviderProfile profile = providerProfileRepository.findByProviderId(provider.getProviderId())
                .orElseGet(() -> ProviderProfile.builder()
                        .providerId(provider.getProviderId())
                        .profileStatus(Status.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .build());

        profile.setBio(request.getBio());
        // profile.setProfilePhotoUrl(request.getProfilePhoto());

        if (request.getGender() != null && !request.getGender().trim().isEmpty()) {
            try {
                profile.setGender(Gender.valueOf(request.getGender().toUpperCase().trim()));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid gender string received: {}", request.getGender());
            }
        }

        if (request.getDob() != null && !request.getDob().trim().isEmpty()) {
            try {
                profile.setDob(LocalDate.parse(request.getDob()));
            } catch (Exception e) {
                log.warn("Invalid DOB string received: {}", request.getDob());
            }
        }

        profile.setUpdatedAt(LocalDateTime.now());
        providerProfileRepository.save(profile);

        // 3. Save or update language
        if (request.getLanguage() != null && !request.getLanguage().trim().isEmpty()) {
            List<ProviderLanguage> languages = providerLanguageRepository.findByProviderId(provider.getProviderId());
            if (languages.isEmpty()) {
                ProviderLanguage lang = ProviderLanguage.builder()
                        .providerId(provider.getProviderId())
                        .language(request.getLanguage().trim())
                        .proficiency(com.bookmypro.provider_service.enums.LanguageProficiency.NATIVE)
                        .build();
                providerLanguageRepository.save(lang);
            } else {
                ProviderLanguage lang = languages.get(0);
                lang.setLanguage(request.getLanguage().trim());
                providerLanguageRepository.save(lang);
            }
        }

        String profilePhotoBase64 = null;
        if (profile.getProfilePhotoUrl() != null && !profile.getProfilePhotoUrl().isBlank()) {
            try {
                byte[] bytes = fileUploadService.getFile(profile.getProfilePhotoUrl());
                String mimeType = "image/jpeg";
                String lowercasePath = profile.getProfilePhotoUrl().toLowerCase();
                if (lowercasePath.endsWith(".png")) {
                    mimeType = "image/png";
                } else if (lowercasePath.endsWith(".gif")) {
                    mimeType = "image/gif";
                } else if (lowercasePath.endsWith(".webp")) {
                    mimeType = "image/webp";
                }
                profilePhotoBase64 = "data:" + mimeType + ";base64," + java.util.Base64.getEncoder().encodeToString(bytes);
            } catch (Exception e) {
                log.warn("Failed to load profile photo file from path: {}", profile.getProfilePhotoUrl());
            }
        }

        return PersonalInfoResponse.builder()
                .providerId(provider.getProviderId())
                .credentialId(provider.getCredentialId())
                .firstName(provider.getFirstName())
                .middleName(provider.getMiddleName())
                .lastName(provider.getLastName())
                .phoneNumber(provider.getPhoneNumber())
                .bio(profile.getBio())
                .profilePhoto(profilePhotoBase64)
                .gender(profile.getGender() != null ? profile.getGender().name() : null)
                .dob(profile.getDob() != null ? profile.getDob().toString() : null)
                .language(request.getLanguage())
                .build();
    }

    @Transactional
    public String updateProfilePhoto(UUID credentialId, org.springframework.web.multipart.MultipartFile file) {
        log.info("Updating profile photo for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        ProviderProfile profile = providerProfileRepository.findByProviderId(provider.getProviderId())
                .orElseGet(() -> ProviderProfile.builder()
                        .providerId(provider.getProviderId())
                        .profileStatus(Status.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .build());

        if (file != null && !file.isEmpty()) {
            java.util.List<String> uploadedPaths = fileUploadService.uploadFile(java.util.List.of(file));
            if (uploadedPaths != null && !uploadedPaths.isEmpty()) {
                String savedPath = uploadedPaths.get(0);
                profile.setProfilePhotoUrl(savedPath);
                profile.setUpdatedAt(LocalDateTime.now());
                providerProfileRepository.save(profile);
                return savedPath;
            }
        }
        throw new BusinessException(ErrorCode.SOMETHING_WENT_WRONG);
    }
}
