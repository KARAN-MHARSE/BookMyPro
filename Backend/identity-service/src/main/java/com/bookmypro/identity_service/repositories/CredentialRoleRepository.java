package com.bookmypro.identity_service.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.identity_service.model.CredentialRole;

public interface CredentialRoleRepository extends JpaRepository<CredentialRole, UUID> {
	
    boolean existsByCredentialIdAndRoleId(UUID credentialId, UUID roleId);
    List<CredentialRole> findAllByCredentialId(UUID credntialId);

}
