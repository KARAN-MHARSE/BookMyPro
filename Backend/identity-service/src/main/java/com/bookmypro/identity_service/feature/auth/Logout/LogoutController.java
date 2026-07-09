package com.bookmypro.identity_service.feature.auth.Logout;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmypro.identity_service.feature.auth.login.CustomUserDetails;
import com.bookmypro.identity_service.model.Credential;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LogoutController {

	private final LogoutService logoutService;

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody LogoutRequest request) {

		logoutService.logout(request);

		return ResponseEntity.ok(Map.of("message", "Logout successful"));
	}

	@PostMapping("/logout-all")
	public ResponseEntity<?> logoutAll(@RequestParam UUID credentialId) {
		logoutService.logoutAll(credentialId);

		return ResponseEntity.ok(Map.of("message", "Logged out from all devices successfully"));
	}

}