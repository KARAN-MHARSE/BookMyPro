package com.bookmypro.provider_service.feature.providerdetails;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/provider")
@RequiredArgsConstructor
public class ProviderDetailsController {
	private final ProviderDetailsService service;

	@GetMapping("/details/{providerId}")
	public ResponseEntity<ProviderDetailsResponse> fetchProviderDetails(@PathVariable UUID providerId){
		return ResponseEntity.ok(service.fetchProviderDetails(providerId));
	}
	
}
