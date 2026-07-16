package com.bookmypro.provider_service.feature.profile.professional_info;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/provider/profile/professional")
@RequiredArgsConstructor
public class ProfessionalInfoController {

    private final ProfessionalInfoService service;

    @GetMapping("/{credentialId}")
    public ResponseEntity<ProfessionalInfoResponse> getProfessionalInfo(@PathVariable UUID credentialId) {
        return ResponseEntity.ok(service.getProfessionalInfoByCredentialId(credentialId));
    }

    @PostMapping("/{credentialId}")
    public ResponseEntity<ProfessionalInfoResponse> saveOrUpdateProfessionalInfo(
            @PathVariable UUID credentialId,
            @Valid @RequestBody ProfessionalInfoRequest request) {
        return ResponseEntity.ok(service.saveOrUpdate(credentialId, request));
    }
}
