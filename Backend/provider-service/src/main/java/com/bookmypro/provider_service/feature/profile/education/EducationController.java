package com.bookmypro.provider_service.feature.profile.education;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/provider/profile/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService service;

    @GetMapping("/provider/{credentialId}")
    public ResponseEntity<List<EducationResponse>> getEducations(@PathVariable UUID credentialId) {
        return ResponseEntity.ok(service.getEducationsByCredentialId(credentialId));
    }

    @PostMapping
    public ResponseEntity<EducationResponse> saveOrUpdateEducation(@Valid @RequestBody EducationRequest request) {
        return ResponseEntity.ok(service.saveOrUpdate(request));
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<Void> deleteEducation(@PathVariable UUID educationId) {
        service.deleteEducation(educationId);
        return ResponseEntity.noContent().build();
    }
}
