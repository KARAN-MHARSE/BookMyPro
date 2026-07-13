package com.bookmypro.identity_service.feature.auth.createcredential;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bookmypro.identity_service.common.enums.CredentialStatus;
import com.bookmypro.identity_service.common.enums.OtpPurpose;
import com.bookmypro.identity_service.common.service.EmailService;
import com.bookmypro.identity_service.common.service.OtpService;
import com.bookmypro.identity_service.common.service.PasswordService;
import com.bookmypro.identity_service.dto.request.EmailRequest;
import com.bookmypro.identity_service.exception.BusinessException;
import com.bookmypro.identity_service.exception.ErrorCode;
import com.bookmypro.identity_service.model.Credential;
import com.bookmypro.identity_service.model.CredentialRole;
import com.bookmypro.identity_service.model.Otp;
import com.bookmypro.identity_service.model.Role;
import com.bookmypro.identity_service.repositories.CredentialRepository;
import com.bookmypro.identity_service.repositories.CredentialRoleRepository;
import com.bookmypro.identity_service.repositories.RoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateCredentialService {
	private final CredentialRepository credentialRepository;
	private final RoleRepository roleRepository;
	private final CredentialRoleRepository credentialRoleRepository;
	private final OtpService otpService;
	private final EmailService emailService;
	private final PasswordService passwordService;

	@Transactional()
	public CreateCredentialResponse createCredential(CreateCredentialRequest request) {

		log.info("Creating credential for email={}", request.getEmail());

		passwordService.validate(request.getPassword());

		Optional<Credential> credentialOptional = credentialRepository.findByEmail(request.getEmail());

		if (credentialOptional.isPresent()) {

			Credential credential = credentialOptional.get();

			if (credential.getEmailVerified()) {
				throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
			}

			log.info("Credential already exists but email is not verified. Resending OTP. credentialId={}",
					credential.getCredentialId());

			otpService.deleteVerificationOtp(credential);

			Otp otp = otpService.createAndSaveOtp(credential,OtpPurpose.EMAIL_VERIFICATION,5);

			sendVerificationOtpEmail(credential.getEmail(), otp.getOtpCode());

			CreateCredentialResponse response = new CreateCredentialResponse();
			response.setCredentialId(credential.getCredentialId().toString());
			response.setMessage("Verification OTP sent successfully.");
			response.setStatus(CredentialStatus.PENDING_VERIFICATION.name());

			return response;
		}

		String encodedPassword = passwordService.encode(request.getPassword());

		Credential credential = Credential.builder().email(request.getEmail()).username(request.getUserName())
				.password(encodedPassword).status(CredentialStatus.PENDING_VERIFICATION).emailVerified(false)
				.phoneVerified(false).build();

		Credential savedCredential = credentialRepository.save(credential);

		saveRoleWithCredential(savedCredential.getCredentialId(), request.getRoleCode());

		log.info("Credential created successfully. credentialId={}", savedCredential.getCredentialId());

		Otp otp = otpService.createAndSaveOtp(savedCredential,OtpPurpose.EMAIL_VERIFICATION,5);

		sendVerificationOtpEmail(savedCredential.getEmail(), otp.getOtpCode());

		CreateCredentialResponse response = new CreateCredentialResponse();
		response.setCredentialId(savedCredential.getCredentialId().toString());
		response.setMessage("Credentials created successfully. Verification OTP sent.");
		response.setStatus(CredentialStatus.PENDING_VERIFICATION.name());

		return response;
	}

	private void saveRoleWithCredential(UUID credentialId, String roleCode) {
		Role role = roleRepository.findByRoleCode(roleCode)
				.orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));

		if (credentialRoleRepository.existsByCredentialIdAndRoleId(credentialId, role.getRoleId())) {
			return;
		}

		CredentialRole cr = CredentialRole.builder().credentialId(credentialId).roleId(role.getRoleId()).build();

		credentialRoleRepository.save(cr);
	}

	private void sendVerificationOtpEmail(String email, String otp) {
		String subject = "Verify your email";

		String body = """
				Welcome to BookMyPro!

				Your email verification OTP is: %s

				This OTP is valid for 5 minutes.

				If you did not create this account, please ignore this email.
				""".formatted(otp);

		EmailRequest emailRequest = new EmailRequest(email, subject, body);
		emailService.sendEmail(emailRequest);

	}

}
