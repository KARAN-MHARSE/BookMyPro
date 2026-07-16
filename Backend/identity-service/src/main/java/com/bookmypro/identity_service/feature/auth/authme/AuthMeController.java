package com.bookmypro.identity_service.feature.auth.authme;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthMeController{

	private final AuthMeService service;
	
	@GetMapping("/me")
	public ResponseEntity<AuthMeResponse> authMe(HttpServletRequest request){
		AuthMeResponse response = service.authenticateMe(request);
		return ResponseEntity.ok(response);
	}

}
