package com.bookmypro.identity_service.feature.auth.verifyotp;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bookmypro.identity_service.common.enums.CredentialStatus;
import com.bookmypro.identity_service.common.enums.OtpPurpose;
import com.bookmypro.identity_service.common.service.OtpService;
import com.bookmypro.identity_service.exception.BusinessException;
import com.bookmypro.identity_service.exception.ErrorCode;
import com.bookmypro.identity_service.model.Credential;
import com.bookmypro.identity_service.repositories.CredentialRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyOtpService {
	private final CredentialRepository credentialRepository;
	private final OtpService otpService;

	@Transactional
	public VerifyOtpResponse validateOtp(VerifyOtpRequest request) {
		Credential credential = credentialRepository.findByCredentialId(UUID.fromString(request.getCredentialId()))
				.orElseThrow(() -> new BusinessException(ErrorCode.CREDENTIAL_NOT_FOUND));

		otpService.verifyEmailVerificationOtp(credential,OtpPurpose.EMAIL_VERIFICATION, request.getOtpCode());

		credential.setEmailVerified(true);
		credential.setStatus(CredentialStatus.ACTIVE);

		return new VerifyOtpResponse("Email verified successfully.");

	}

}
