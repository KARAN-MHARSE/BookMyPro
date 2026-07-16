package com.bookmypro.provider_service.repository;

import com.bookmypro.provider_service.model.ProviderLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProviderLanguageRepository extends JpaRepository<ProviderLanguage, UUID> {
    List<ProviderLanguage> findByProviderId(UUID providerId);
}
