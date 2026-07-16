package com.bookmypro.provider_service.feature.profile.bank;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.provider_service.enums.AccountType;
import com.bookmypro.provider_service.enums.VerificationStatus;
import com.bookmypro.provider_service.exception.BusinessException;
import com.bookmypro.provider_service.exception.ErrorCode;
import com.bookmypro.provider_service.model.Provider;
import com.bookmypro.provider_service.model.ProviderBank;
import com.bookmypro.provider_service.repository.ProviderBankRepository;
import com.bookmypro.provider_service.repository.ProviderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankInfoService {

    private final ProviderRepository providerRepository;
    private final ProviderBankRepository providerBankRepository;

    public BankInfoResponse getBankInfoByCredentialId(UUID credentialId) {
        log.info("Fetching bank details for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        Optional<ProviderBank> bankOpt = providerBankRepository.findByProviderId(provider.getProviderId());

        if (bankOpt.isEmpty()) {
            return BankInfoResponse.builder()
                    .providerId(provider.getProviderId())
                    .build();
        }

        return mapToResponse(bankOpt.get());
    }

    @Transactional
    public BankInfoResponse saveOrUpdate(UUID credentialId, BankInfoRequest request) {
        log.info("Saving or updating bank details for credentialId={}", credentialId);

        Provider provider = providerRepository.findByCredentialId(credentialId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROVIDER_NOT_FOUND));

        ProviderBank bank = providerBankRepository.findByProviderId(provider.getProviderId())
                .orElseGet(() -> ProviderBank.builder()
                        .providerId(provider.getProviderId())
                        .createdAt(LocalDateTime.now())
                        .verificationStatus(VerificationStatus.PENDING)
                        .build());

        bank.setAccountHolderName(request.getAccountHolderName());
        bank.setBankName(request.getBankName());
        bank.setAccountNumber(request.getAccountNumber());
        bank.setIfscCode(request.getIfscCode());
        bank.setBranchName(request.getBranchName());
        bank.setUpiId(request.getUpiId());
        bank.setPanNumber(request.getPanNumber());
        bank.setGstNumber(request.getGstNumber());
        bank.setIsPrimary(request.getIsPrimary());

        if (request.getAccountType() != null && !request.getAccountType().trim().isEmpty()) {
            try {
                bank.setAccountType(AccountType.valueOf(request.getAccountType().toUpperCase().trim()));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid account type: {}", request.getAccountType());
            }
        }

        bank.setUpdatedAt(LocalDateTime.now());
        ProviderBank saved = providerBankRepository.save(bank);

        return mapToResponse(saved);
    }

    private BankInfoResponse mapToResponse(ProviderBank bank) {
        return BankInfoResponse.builder()
                .bankId(bank.getBankId())
                .providerId(bank.getProviderId())
                .accountHolderName(bank.getAccountHolderName())
                .bankName(bank.getBankName())
                .accountNumber(bank.getAccountNumber())
                .confirmAccountNumber(bank.getAccountNumber())
                .ifscCode(bank.getIfscCode())
                .branchName(bank.getBranchName())
                .accountType(bank.getAccountType() != null ? bank.getAccountType().name() : null)
                .upiId(bank.getUpiId())
                .panNumber(bank.getPanNumber())
                .gstNumber(bank.getGstNumber())
                .isPrimary(bank.getIsPrimary())
                .verificationStatus(bank.getVerificationStatus() != null ? bank.getVerificationStatus().name() : null)
                .build();
    }
}
