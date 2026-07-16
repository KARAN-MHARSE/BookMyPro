package com.bookmypro.provider_service.repository;

import com.bookmypro.provider_service.model.ProviderAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProviderAvailabilityRepository extends JpaRepository<ProviderAvailability, UUID> {
    java.util.List<ProviderAvailability> findByProviderId(UUID providerId);
    void deleteByProviderId(UUID providerId);
}
