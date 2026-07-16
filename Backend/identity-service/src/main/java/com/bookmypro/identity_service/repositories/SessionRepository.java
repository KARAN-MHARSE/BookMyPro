package com.bookmypro.identity_service.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookmypro.identity_service.model.Credential;
import com.bookmypro.identity_service.model.Session;

public interface SessionRepository extends JpaRepository<Session, UUID> {
	long countByCredentialIdAndStatus(UUID credentialId, String status);

	Optional<Session> findByCredentialIdAndDeviceIdAndStatus(UUID credentialId, UUID deviceId, String status);
	
	
	@Modifying
	@Query("""
	    UPDATE Session s
	    SET s.status = 'INACTIVE',
	        s.lastAccessed = CURRENT_TIMESTAMP
	    WHERE s.credentialId = :credentialId
	      AND s.status = 'ACTIVE'
	""")
	void closeAllSessions(@Param("credentialId") UUID credentialId);
}
