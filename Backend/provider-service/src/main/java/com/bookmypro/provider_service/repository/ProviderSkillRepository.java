package com.bookmypro.provider_service.repository;

import com.bookmypro.provider_service.model.ProviderSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProviderSkillRepository extends JpaRepository<ProviderSkill, UUID> {
	@Query("SELECT ps.skillId FROM ProviderSkill ps WHERE ps.providerId = :providerId")
	List<UUID> findAllSkillIdsByProviderId(@Param("providerId") UUID providerId);
}
