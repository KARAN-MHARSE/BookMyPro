package com.bookmypro.identity_service.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.identity_service.model.Credential;
import com.bookmypro.identity_service.model.PasswordHistory;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, UUID>{

	boolean existsByCredentialAndPasswordHash(Credential credential, String encodedPassword);

	List<PasswordHistory> findByCredential(Credential credential);

	List<PasswordHistory> findTop5ByCredentialOrderByChangedAtDesc(Credential credential);

}
