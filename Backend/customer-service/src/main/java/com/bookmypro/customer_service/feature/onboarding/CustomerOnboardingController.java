package com.bookmypro.customer_service.feature.onboarding;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerOnboardingController {
	
	private final CustomerOnboardingService customerOnboardingService;
	
	
	@PostMapping("/onboard")
	public ResponseEntity<CustomerOnboardingResponse> onboardCustomer(
			@RequestBody CustomerOnboardingRequest request
			){
		CustomerOnboardingResponse response  =customerOnboardingService.onboardCustomer(request);
		return ResponseEntity.ok(response);
	}

}
