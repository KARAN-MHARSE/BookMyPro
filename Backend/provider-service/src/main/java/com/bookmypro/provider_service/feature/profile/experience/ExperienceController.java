package com.bookmypro.provider_service.feature.profile.experience;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/provider/profile/experience")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService service;

    @GetMapping("/provider/{credentialId}")
    public ResponseEntity<List<ExperienceResponse>> getExperiences(@PathVariable UUID credentialId) {
        return ResponseEntity.ok(service.getExperiencesByCredentialId(credentialId));
    }

    @PostMapping
    public ResponseEntity<ExperienceResponse> saveOrUpdateExperience(@Valid @RequestBody ExperienceRequest request) {
        return ResponseEntity.ok(service.saveOrUpdate(request));
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<Void> deleteExperience(@PathVariable UUID experienceId) {
        service.deleteExperience(experienceId);
        return ResponseEntity.noContent().build();
    }
}
