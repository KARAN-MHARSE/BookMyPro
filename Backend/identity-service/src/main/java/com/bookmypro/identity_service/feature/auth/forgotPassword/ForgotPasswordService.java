package com.bookmypro.identity_service.feature.auth.forgotPassword;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookmypro.identity_service.common.enums.CredentialStatus;
import com.bookmypro.identity_service.common.enums.OtpPurpose;
import com.bookmypro.identity_service.common.service.EmailService;
import com.bookmypro.identity_service.common.service.OtpService;
import com.bookmypro.identity_service.common.service.PasswordService;
import com.bookmypro.identity_service.dto.request.EmailRequest;
import com.bookmypro.identity_service.exception.BusinessException;
import com.bookmypro.identity_service.exception.ErrorCode;
import com.bookmypro.identity_service.model.Credential;
import com.bookmypro.identity_service.model.Otp;
import com.bookmypro.identity_service.model.PasswordHistory;
import com.bookmypro.identity_service.repositories.CredentialRepository;
import com.bookmypro.identity_service.repositories.OtpRepository;
import com.bookmypro.identity_service.repositories.PasswordHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordService {
	private final CredentialRepository credentialRepository;
	private final OtpRepository otpRepository;
	private final OtpService otpService;
	private final EmailService emailService;
	private final PasswordService passwordService;
	private final PasswordHistoryRepository passwordHistoryRepository;

	@Transactional
	public ForgotPasswordResponse validateAndSendForgotPasswordOtp(ForgotPasswordRequest request) {
		Credential credential = credentialRepository.findByEmailAndStatus(request.getEmail(), CredentialStatus.ACTIVE)
				.orElseThrow(() -> new BusinessException(ErrorCode.CREDENTIAL_NOT_FOUND));

		if (passwordService.matches(request.getPassword(), credential.getPassword())) {
			throw new BusinessException(ErrorCode.ALREADY_USED_PASSWORD);
		}

		List<PasswordHistory> histories = passwordHistoryRepository.findByCredential(credential);

		for (PasswordHistory history : histories) {

			if (passwordService.matches(request.getPassword(), history.getPasswordHash())) {

				throw new BusinessException(ErrorCode.ALREADY_USED_PASSWORD);
			}
		}

		log.info("Sending forgot password OTP to {}", request.getEmail());
		Otp otp = otpService.createAndSaveOtp(credential, OtpPurpose.FORGOT_PASSWORD, 1);

		log.info("Forgot password OTP sent successfully to {}", request.getEmail());

		sendForgotOtpEmail(request.getEmail(), otp.getOtpCode());

		return new ForgotPasswordResponse("Otp send successfully");

	}

	private void sendForgotOtpEmail(String email, String otp) {
		String subject = "Reset Your Password";

		String body = """
				Hello,

				We received a request to reset your BookMyPro account password.

				Your OTP is: %s

				This OTP is valid for 5 minutes.

				If you didn't request a password reset, you can safely ignore this email.

				Regards,
				BookMyPro Team
				""".formatted(otp);

		EmailRequest emailRequest = new EmailRequest(email, subject, body);
		emailService.sendEmail(emailRequest);

	}

	@Transactional
	public ForgotPasswordResponse validateOtpAndSaveForgotPassword(ForgotPasswordOtpVerifiedRequest request) {
		Credential credential = credentialRepository.findByEmailAndStatus(request.getEmail(), CredentialStatus.ACTIVE)
				.orElseThrow(() -> new BusinessException(ErrorCode.CREDENTIAL_NOT_FOUND));

		passwordService.validate(request.getPassword());

		otpService.verifyEmailVerificationOtp(credential, OtpPurpose.FORGOT_PASSWORD, request.getOtp());

		String encodedPassword = passwordService.encode(request.getPassword());

		PasswordHistory passwordHistory = PasswordHistory.builder().changedAt(LocalDateTime.now())
				.credential(credential).passwordHash(encodedPassword).build();

		passwordHistoryRepository.save(passwordHistory);

		credential.setPassword(encodedPassword);
		
		credentialRepository.save(credential);

		return new ForgotPasswordResponse("Password changed successfully.");

	}

}
