package com.bookmypro.identity_service.feature.auth.forgotPassword;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/forgot-password")
@RequiredArgsConstructor
public class ForgotPassowrdController {
	private final ForgotPasswordService service;
	
	@PostMapping("/send-otp")
	public ResponseEntity<ForgotPasswordResponse> validateAndSendForgotPasswordOtp(@RequestBody ForgotPasswordRequest request){
		ForgotPasswordResponse response = service.validateAndSendForgotPasswordOtp(request);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/validate-otp")
	public ResponseEntity<ForgotPasswordResponse> validateOtpAndSaveForgotPassword(@RequestBody ForgotPasswordOtpVerifiedRequest request){
		ForgotPasswordResponse response = service.validateOtpAndSaveForgotPassword(request);
		return ResponseEntity.ok(response);
	}

}
