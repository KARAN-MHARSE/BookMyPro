package com.bookmypro.provider_service.feature.provideronboarding;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderOnboardingResponse {

	private UUID providerId;
	private UUID credentialId;
	private String message;

}
