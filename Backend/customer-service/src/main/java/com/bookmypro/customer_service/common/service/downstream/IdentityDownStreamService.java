package com.bookmypro.customer_service.common.service.downstream;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.bookmypro.customer_service.common.request.CreateCredentialRequest;
import com.bookmypro.customer_service.common.response.CreateCredentialResponse;

import jakarta.validation.Valid;

@HttpExchange
public interface IdentityDownStreamService {

	@PostExchange("/auth/credential")
	public ResponseEntity<CreateCredentialResponse> createCredentials(@Valid @RequestBody CreateCredentialRequest request);

	@DeleteMapping("/credentials/{credentialId}")
	public void deleteCredential(@PathVariable UUID credentialId);	
	
}
