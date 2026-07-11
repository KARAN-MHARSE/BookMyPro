package com.bookmypro.customer_service.common.response;

import lombok.Data;

@Data
public class CreateCredentialResponse {
	private String credentialId;
	private String status;
	private String message;
}
