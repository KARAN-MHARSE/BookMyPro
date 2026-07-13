package com.bookmypro.identity_service.common.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.hibernate.annotations.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.cors.CorsConfigurationSource;
import com.bookmypro.identity_service.common.enums.OtpPurpose;
import com.bookmypro.identity_service.exception.BusinessException;
import com.bookmypro.identity_service.exception.ErrorCode;
import com.bookmypro.identity_service.model.Credential;
import com.bookmypro.identity_service.model.Otp;
import com.bookmypro.identity_service.repositories.OtpRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService {

	private static final SecureRandom RANDOM = new SecureRandom();
	private final OtpRepository otpRepository;
	@Autowired
	@Lazy
	private OtpService self;
	private static final int MAX_ATTEMPTS = 5;

	@Transactional
	public Otp createAndSaveOtp(Credential credential, OtpPurpose purpose, Integer time) {
//		otpRepository.deleteByCredentialAndPurpose(credential, purpose);

		String otpCode = generateOtp();

		Otp otp = Otp.builder().credential(credential).purpose(purpose).otpCode(otpCode).createdAt(LocalDateTime.now())
				.attemptCount(0).expiresAt(LocalDateTime.now().plusMinutes(time)).build();

		return otpRepository.save(otp);
	}

	private String generateOtp() {

		int value = 100000 + RANDOM.nextInt(900000);

		return String.valueOf(value);
	}

	@Transactional
	public void verifyEmailVerificationOtp(Credential credential, OtpPurpose purpose, String otpCode) {
		Otp otp = otpRepository.findFirstByCredentialAndPurposeOrderByCreatedAtDesc(credential, purpose)
				.orElseThrow(() -> new BusinessException(ErrorCode.OTP_NOT_FOUND));

		if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new BusinessException(ErrorCode.OTP_EXPIRED);
		}

		if (otp.getAttemptCount() >= MAX_ATTEMPTS) {
			throw new BusinessException(ErrorCode.OTP_MULTIPLE_ATTEMPT);
		}

		if (!otp.getOtpCode().equals(otpCode)) {
			int attemptsAfterThis = self.incrementAttempt(otp);

			if (attemptsAfterThis >= MAX_ATTEMPTS) {
				throw new BusinessException(ErrorCode.OTP_MULTIPLE_ATTEMPT);
			}
			throw new BusinessException(ErrorCode.INVALID_OTP);
		}

		otpRepository.deleteByCredentialAndPurpose(credential, purpose);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int incrementAttempt(Otp otp) {
		otp.setAttemptCount(otp.getAttemptCount() + 1);
		Otp saved = otpRepository.save(otp);
		return saved.getAttemptCount();
	}

	public void deleteVerificationOtp(Credential credential) {
		otpRepository.deleteByCredentialAndPurpose(credential, OtpPurpose.EMAIL_VERIFICATION);

	}
}
