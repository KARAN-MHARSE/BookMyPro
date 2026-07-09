package com.bookmypro.identity_service.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bookmypro.identity_service.model.Credential;
import com.bookmypro.identity_service.model.Session;

public interface SessionRepository extends JpaRepository<Session, UUID> {
	long countByCredentialAndStatus(Credential credential, String status);

	Optional<Session> findByCredentialAndDeviceAndStatus(Credential credential, String device, String status);
	
	@Modifying
    @Query("""
        UPDATE Session s
        SET s.status = 'INACTIVE',
            s.lastAccessed = CURRENT_TIMESTAMP
        WHERE s.credential = :credential
        AND s.status = 'ACTIVE'
    """)
    void closeAllSessions(
            Credential credential
    );
}
