package com.bookmypro.identity_service.feature.auth.delateCredential;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.identity_service.exception.BusinessException;
import com.bookmypro.identity_service.exception.ErrorCode;
import com.bookmypro.identity_service.model.Credential;
import com.bookmypro.identity_service.repositories.CredentialRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteCredentialService {
	private final CredentialRepository credentialRepository;
	
	
	@Transactional
	public void deleteCredential(UUID credentialId) {

	    Credential credential = credentialRepository.findById(credentialId)
	            .orElseThrow(() -> new BusinessException(ErrorCode.CREDENTIAL_NOT_FOUND));

	    credentialRepository.delete(credential);
	}

}
