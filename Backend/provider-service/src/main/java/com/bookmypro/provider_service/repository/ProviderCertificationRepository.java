package com.bookmypro.provider_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookmypro.provider_service.model.ProviderCertification;

@Repository
public interface ProviderCertificationRepository extends JpaRepository<ProviderCertification, UUID> {
	@Query("""
			SELECT pc.certificateName
			FROM ProviderCertification pc
			WHERE pc.providerId = :providerId
			""")
	List<String> findAllCertificateNamesByProviderId(@Param("providerId") UUID providerId);

}
