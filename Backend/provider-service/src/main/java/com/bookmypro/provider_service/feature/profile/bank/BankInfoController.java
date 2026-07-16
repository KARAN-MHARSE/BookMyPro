package com.bookmypro.provider_service.feature.profile.bank;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/provider/profile/bank")
@RequiredArgsConstructor
public class BankInfoController {

    private final BankInfoService service;

    @GetMapping("/{credentialId}")
    public ResponseEntity<BankInfoResponse> getBankInfo(@PathVariable UUID credentialId) {
        return ResponseEntity.ok(service.getBankInfoByCredentialId(credentialId));
    }

    @PostMapping("/{credentialId}")
    public ResponseEntity<BankInfoResponse> saveOrUpdateBankInfo(
            @PathVariable UUID credentialId,
            @Valid @RequestBody BankInfoRequest request) {
        return ResponseEntity.ok(service.saveOrUpdate(credentialId, request));
    }
}
