package com.bookmypro.identity_service.feature.auth.verifyotp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class VerifyOtpController {
	private final VerifyOtpService verifyOtpService;

	
	  @PostMapping("/otp/verify-otp")
	    public ResponseEntity<VerifyOtpResponse> validateOtp(@RequestBody @Valid VerifyOtpRequest request){
		  VerifyOtpResponse response =verifyOtpService.validateOtp(request);
	    	return ResponseEntity.ok(response);
	    }
}
