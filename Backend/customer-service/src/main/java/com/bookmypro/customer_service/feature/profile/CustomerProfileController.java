package com.bookmypro.customer_service.feature.profile;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerProfileController {
	private final CustomerProfileService profileService;

	@PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> createOrUpdateCustomerProfile(@ModelAttribute CustomerProfileRequest request) {
		profileService.createOrUpdateCustomerProfile(request);
		return ResponseEntity.ok(true);
	}
	
	@GetMapping(value = "/profile/{credentialId}")
	public ResponseEntity<CustomerProfileResponse> createOrUpdateCustomerProfile(@PathVariable UUID credentialId) {
		CustomerProfileResponse response = profileService.getCustomerProfile(credentialId);
		return ResponseEntity.ok(response);
	}

}
