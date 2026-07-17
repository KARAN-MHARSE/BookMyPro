package com.bookmypro.identity_service.feature.auth.deletecredential;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DeleteCredentialController {
	private final DeleteCredentialService deleteCredentialService;

	@DeleteMapping("/credentials/{credentialId}")
	public void deleteCredential(@PathVariable UUID credentialId) {
		deleteCredentialService.deleteCredential(credentialId);
	}
}
