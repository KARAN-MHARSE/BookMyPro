package com.bookmypro.provider_service.feature.profile.services;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/provider/profile/services")
@RequiredArgsConstructor
public class ProviderServicesController {

    private final ProviderServicesService service;

    @GetMapping("/{credentialId}")
    public ResponseEntity<ServiceResponse> getServices(@PathVariable UUID credentialId) {
        return ResponseEntity.ok(service.getServicesByCredentialId(credentialId));
    }

    @PostMapping
    public ResponseEntity<ServiceDto> saveOrUpdateService(@Valid @RequestBody ServiceRequest request) {
        return ResponseEntity.ok(service.saveOrUpdate(request));
    }

    @DeleteMapping("/{providerServiceId}")
    public ResponseEntity<Void> deleteService(@PathVariable UUID providerServiceId) {
        service.deleteService(providerServiceId);
        return ResponseEntity.noContent().build();
    }
}
