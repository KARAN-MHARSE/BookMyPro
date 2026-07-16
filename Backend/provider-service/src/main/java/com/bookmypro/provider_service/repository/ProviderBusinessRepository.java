package com.bookmypro.provider_service.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bookmypro.provider_service.model.ProviderBusiness;

@Repository
public interface ProviderBusinessRepository extends JpaRepository<ProviderBusiness, UUID> {
    Optional<ProviderBusiness> findByProviderId(UUID providerId);
}
