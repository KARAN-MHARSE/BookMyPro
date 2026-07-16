package com.bookmypro.provider_service.feature.providerOnboarding;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/provider")
@RequiredArgsConstructor
public class ProviderOnboardingController {
	private final ProviderOnboardingService service;

	@PostMapping("/onboard")
	public ResponseEntity<ProviderOnboardingResponse> onboardCustomer(@RequestBody ProviderOnboardingRequest request) {
		ProviderOnboardingResponse response = service.onboardProvider(request);
		return ResponseEntity.ok(response);
	}

}
