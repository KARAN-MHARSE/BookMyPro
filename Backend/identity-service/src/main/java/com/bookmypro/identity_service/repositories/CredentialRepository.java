package com.bookmypro.identity_service.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.identity_service.common.enums.CredentialStatus;
import com.bookmypro.identity_service.model.Credential;

public interface CredentialRepository extends JpaRepository<Credential, UUID> {
	Optional<Credential> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<Credential> findByCredentialId(UUID credentialId);
	
	Optional<Credential> findByEmailAndStatus(String email,CredentialStatus status);


}
