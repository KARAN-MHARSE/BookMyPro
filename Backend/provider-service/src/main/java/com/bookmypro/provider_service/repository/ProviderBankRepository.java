package com.bookmypro.provider_service.repository;

import com.bookmypro.provider_service.model.ProviderBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProviderBankRepository extends JpaRepository<ProviderBank, UUID> {
}
