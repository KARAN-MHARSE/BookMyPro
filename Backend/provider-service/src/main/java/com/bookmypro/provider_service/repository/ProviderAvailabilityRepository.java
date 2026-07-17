package com.bookmypro.provider_service.repository;

import com.bookmypro.provider_service.model.ProviderAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProviderAvailabilityRepository extends JpaRepository<ProviderAvailability, UUID> {
    java.util.List<ProviderAvailability> findByProviderId(UUID providerId);

    java.util.List<ProviderAvailability> findByProviderIdIn(java.util.List<UUID> providerIds);
    void deleteByProviderId(UUID providerId);
}
