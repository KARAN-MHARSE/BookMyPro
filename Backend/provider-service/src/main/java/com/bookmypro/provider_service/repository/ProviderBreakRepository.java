package com.bookmypro.provider_service.repository;

import com.bookmypro.provider_service.model.ProviderBreak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProviderBreakRepository extends JpaRepository<ProviderBreak, UUID> {
    java.util.Optional<ProviderBreak> findByAvailabilityId(UUID availabilityId);
    void deleteByAvailabilityId(UUID availabilityId);
    void deleteByAvailabilityIdIn(java.util.List<UUID> availabilityIds);
}
