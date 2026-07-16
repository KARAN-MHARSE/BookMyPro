package com.bookmypro.identity_service.feature.auth.Logout;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.identity_service.exception.BusinessException;
import com.bookmypro.identity_service.exception.ErrorCode;
import com.bookmypro.identity_service.model.Credential;
import com.bookmypro.identity_service.model.RefreshToken;
import com.bookmypro.identity_service.repositories.CredentialRepository;
import com.bookmypro.identity_service.repositories.RefreshTokenRepository;
import com.bookmypro.identity_service.repositories.SessionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService {
	private final RefreshTokenRepository refreshTokenRepository;
	private final SessionRepository sessionRepository;
	private final CredentialRepository credentialRepository;

	@Transactional
	public void logout(LogoutRequest request) {
		RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
				.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN));

		refreshToken.setRevoked(true);

		refreshTokenRepository.save(refreshToken);

		Credential credential = refreshToken.getCredential();

		sessionRepository.findByCredentialIdAndDeviceIdAndStatus(credential.getCredentialId(), request.getDeviceId(), "ACTIVE")
				.ifPresent(session -> {
					session.setStatus("INACTIVE");
					session.setLastAccessed(LocalDateTime.now());
					sessionRepository.save(session);
				});
	}

	@Transactional
	public void logoutAll(UUID credentialId) {
		Credential credential = credentialRepository.findByCredentialId(credentialId)
				.orElseThrow(()-> new BusinessException(ErrorCode.CREDENTIAL_NOT_FOUND));
		refreshTokenRepository.revokeAllByCredential(credential);

		sessionRepository.closeAllSessions(credential.getCredentialId());
	}

}
