package com.bookmypro.provider_service.repository;

import com.bookmypro.provider_service.model.ProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProviderProfileRepository extends JpaRepository<ProviderProfile, UUID> {
    Optional<ProviderProfile> findByProviderId(UUID providerId);

    List<ProviderProfile> findByProviderIdIn(List<UUID> providerIds);
}
