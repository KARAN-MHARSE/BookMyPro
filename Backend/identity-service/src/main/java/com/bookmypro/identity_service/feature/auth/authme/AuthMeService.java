package com.bookmypro.identity_service.feature.auth.authme;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bookmypro.identity_service.common.service.JwtService;
import com.bookmypro.identity_service.exception.BusinessException;
import com.bookmypro.identity_service.exception.ErrorCode;
import com.bookmypro.identity_service.model.CredentialRole;
import com.bookmypro.identity_service.model.Role;
import com.bookmypro.identity_service.model.Session;
import com.bookmypro.identity_service.repositories.CredentialRepository;
import com.bookmypro.identity_service.repositories.CredentialRoleRepository;
import com.bookmypro.identity_service.repositories.RoleRepository;
import com.bookmypro.identity_service.repositories.SessionRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthMeService {
	private final JwtService jwtService;
	private final RoleRepository roleRepository;
	private final CredentialRoleRepository credentialRoleRepository;
	private final SessionRepository sessionRepository;

	public AuthMeResponse authenticateMe(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		
	    String deviceIdStr = request.getHeader("deviceId"); 
	    
	    // You can now log, validate, or bind the deviceId here:
	    log.info("Authenticating request for deviceId: {}", deviceIdStr);

		if (authorizationHeader == null || deviceIdStr ==null || deviceIdStr.isBlank() || authorizationHeader.isBlank()
				|| !authorizationHeader.startsWith("Bearer ")) {
			throw new BusinessException(ErrorCode.NOT_AUTHORIZED);
		}
		
		UUID deviceId = UUID.fromString(deviceIdStr);
		String token = authorizationHeader.substring(7);
		
		if (!jwtService.validateToken(token)) {
	        throw new BusinessException(ErrorCode.NOT_AUTHORIZED);
	    }
		
		String credentialId = jwtService.extractCredentialId(token);
		String userName = jwtService.extractUsername(token);

		
		sessionRepository.findByCredentialIdAndDeviceIdAndStatus(UUID.fromString(credentialId), deviceId, "ACTIVE")
				.orElseThrow(()-> new BusinessException(ErrorCode.NOT_AUTHORIZED));
		
		
		List<String> roles = getCredentialRoles(UUID.fromString(credentialId));
		
		
		return new AuthMeResponse(userName,credentialId,roles);
	}

	private List<String> getCredentialRoles(UUID credentialId) {
		List<CredentialRole> credentialRoles = credentialRoleRepository.findAllByCredentialId(credentialId);

		List<UUID> roleIds = credentialRoles.stream().map(CredentialRole::getRoleId).toList();

		List<Role> roles = roleRepository.findByRoleIdIn(roleIds);

		return roles.stream().map(Role::getRoleCode).toList();
	}
}
