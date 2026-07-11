package com.bookmypro.identity_service.feature.auth.login;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.identity_service.common.enums.CredentialStatus;
import com.bookmypro.identity_service.common.service.JwtService;
import com.bookmypro.identity_service.common.service.PasswordService;
import com.bookmypro.identity_service.exception.BusinessException;
import com.bookmypro.identity_service.exception.ErrorCode;
import com.bookmypro.identity_service.model.Credential;
import com.bookmypro.identity_service.model.CredentialRole;
import com.bookmypro.identity_service.model.LoginHistory;
import com.bookmypro.identity_service.model.RefreshToken;
import com.bookmypro.identity_service.model.Role;
import com.bookmypro.identity_service.model.Session;
import com.bookmypro.identity_service.repositories.CredentialRepository;
import com.bookmypro.identity_service.repositories.CredentialRoleRepository;
import com.bookmypro.identity_service.repositories.LoginHistoryRepository;
import com.bookmypro.identity_service.repositories.RefreshTokenRepository;
import com.bookmypro.identity_service.repositories.RoleRepository;
import com.bookmypro.identity_service.repositories.SessionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
	private final RefreshTokenRepository refreshTokenRepository;
	private final CredentialRepository credentialRepository;
	private final LoginHistoryRepository loginHistoryRepository;
	private final RoleRepository roleRepository;
	private final CredentialRoleRepository credentialRoleRepository;
	private final SessionRepository sessionRepository;
	private final PasswordService passwordService;
	private final JwtService jwtService;

	@Value("${MAX_LOGIN_SESSION}")
	private Integer maxLoginSessionsLimit;

	@Autowired
	@Lazy
	private LoginService self;

	@Transactional
	public LoginResponse loginUser(LoginRequest request) {
		LoginResponse response = new LoginResponse();
		Credential credential = credentialRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new BusinessException(ErrorCode.CREDENTIAL_NOT_FOUND));

		if (credential.getStatus() == CredentialStatus.LOCKED) {
			throw new BusinessException(ErrorCode.ACCOUNT_LOCKED);
		}

		if (!credential.getEmailVerified()) {
			throw new BusinessException(ErrorCode.EMAIL_NOT_VERIFIED);
		}

		if (!passwordService.matches(request.getPassword(), credential.getPassword())) {
			self.recordFailedAttempt(credential);
			throw new BusinessException(ErrorCode.INVALID_PASSWORD);
		}

		long loginSessionsCount = sessionRepository.countByCredentialAndStatus(credential, "ACTIVE");
		if (loginSessionsCount >= maxLoginSessionsLimit) {
			throw new BusinessException(ErrorCode.SESSION_LIMIT_EXCEEDED);
		}

		LocalDateTime now = LocalDateTime.now();

		CustomUserDetails principal = new CustomUserDetails(credential);

		String accessToken = jwtService.generateAccessToken(principal);
		String refreshToken = jwtService.generateRefreshToken(principal);

		response.setAccessToken(accessToken);

		RefreshToken token = RefreshToken.builder().credential(credential).createdAt(LocalDateTime.now())
				.expiresAt(jwtService.extractExpiredAt(refreshToken).toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate())
				.deviceId(request.getDeviceId()).deviceName(request.getDeviceName()).ipAddress(request.getIpAddress())
				.token(refreshToken).revoked(false).build();

		refreshTokenRepository.save(token);

		String deviceId = createOrUpdateSession(request, credential);

//		Get role of user
		List<String> roles = getCredentialRoles(credential.getCredentialId());
		response.setRoles(roles);

		LoginHistory loginHistory = LoginHistory.builder().credential(credential).loginTime(now)
				.device(request.getDeviceId()).browser(request.getBrowser()).ipAddress(request.getIpAddress())
				.status("ACTIVE").build();

		loginHistoryRepository.save(loginHistory);

		credential.setFailedLoginAttempts(0);

		response.setDeviceId(deviceId);
		response.setMessage("Login successful");
		return response;

	}

	private List<String> getCredentialRoles(UUID credentialId) {
		List<CredentialRole> credentialRoles = credentialRoleRepository.findAllByCredentialId(credentialId);

		List<UUID> roleIds = credentialRoles.stream().map(CredentialRole::getRoleId).toList();

		List<Role> roles = roleRepository.findByRoleIdIn(roleIds);

		return roles.stream().map(Role::getRoleCode).toList();
	}

	private String createOrUpdateSession(LoginRequest request, Credential credential) {
		String deviceId = request.getDeviceId();

		if (deviceId == null || deviceId.equals("null") || deviceId.trim().isEmpty()) {
			deviceId = UUID.randomUUID().toString();
		}

		Optional<Session> existingSessionOpt = sessionRepository.findByCredentialAndDeviceAndStatus(credential,
				deviceId, "ACTIVE");

		Session session;
		LocalDateTime now = LocalDateTime.now();

		if (existingSessionOpt.isPresent()) {
			session = existingSessionOpt.get();
			session.setLastAccessed(now);
			session.setBrowser(request.getBrowser());
			session.setLocation(request.getLocation());
		} else {
			session = Session.builder().credential(credential).device(deviceId).browser(request.getBrowser())
					.location(request.getLocation()).status("ACTIVE").createdAt(now).lastAccessed(now).build();
		}

		sessionRepository.save(session);

		return deviceId;

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void recordFailedAttempt(Credential credential) {
		credential.setFailedLoginAttempts(credential.getFailedLoginAttempts() + 1);

		if (credential.getFailedLoginAttempts() >= 5) {
			credential.setStatus(CredentialStatus.LOCKED);
		}

		credentialRepository.save(credential);
	}

}
