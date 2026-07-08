package com.bookmypro.identity_service.feature.auth.createcredential;

import lombok.Data;

@Data
public class CreateCredentialResponse {
	private String credentialId;
	private String status;
	private String message;
}
