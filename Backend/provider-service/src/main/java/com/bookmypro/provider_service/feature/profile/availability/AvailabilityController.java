package com.bookmypro.provider_service.feature.profile.availability;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/provider/profile/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService service;

    @GetMapping("/{credentialId}")
    public ResponseEntity<AvailabilityResponse> getAvailability(@PathVariable UUID credentialId) {
        return ResponseEntity.ok(service.getAvailabilityByCredentialId(credentialId));
    }

    @PostMapping("/{credentialId}")
    public ResponseEntity<AvailabilityResponse> saveOrUpdateAvailability(
            @PathVariable UUID credentialId,
            @Valid @RequestBody AvailabilityRequest request) {
        return ResponseEntity.ok(service.saveOrUpdate(credentialId, request));
    }
}
