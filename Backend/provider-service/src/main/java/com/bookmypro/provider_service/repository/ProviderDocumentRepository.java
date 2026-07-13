package com.bookmypro.provider_service.repository;

import com.bookmypro.provider_service.model.ProviderDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProviderDocumentRepository extends JpaRepository<ProviderDocument, UUID> {
}
