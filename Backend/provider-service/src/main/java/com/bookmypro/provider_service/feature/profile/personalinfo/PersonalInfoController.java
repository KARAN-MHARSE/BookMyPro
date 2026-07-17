package com.bookmypro.provider_service.feature.profile.personalinfo;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/provider/profile/personal")
@RequiredArgsConstructor
public class PersonalInfoController {

    private final PersonalInfoService service;

    @GetMapping("/{credentialId}")
    public ResponseEntity<PersonalInfoResponse> getProfile(@PathVariable UUID credentialId) {
        return ResponseEntity.ok(service.getProfileByCredentialId(credentialId));
    }

    @PostMapping("/{credentialId}")
    public ResponseEntity<PersonalInfoResponse> saveOrUpdateProfile(
            @PathVariable UUID credentialId,
            @Valid @RequestBody ProfileRequest request) {
        return ResponseEntity.ok(service.saveOrUpdate(credentialId, request));
    }

    @PostMapping(value = "/{credentialId}/photo", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<java.util.Map<String, String>> updateProfilePhoto(
            @PathVariable UUID credentialId,
            @org.springframework.web.bind.annotation.RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        String photoUrl = service.updateProfilePhoto(credentialId, file);
        return ResponseEntity.ok(java.util.Map.of("photoUrl", photoUrl));
    }
}
