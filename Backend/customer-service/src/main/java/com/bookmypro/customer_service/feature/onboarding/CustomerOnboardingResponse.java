package com.bookmypro.customer_service.feature.onboarding;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOnboardingResponse {

	private UUID customerId;
	private UUID credentialId;
	private String message;

}
