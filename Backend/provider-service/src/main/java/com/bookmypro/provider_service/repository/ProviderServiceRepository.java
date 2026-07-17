package com.bookmypro.provider_service.repository;

import com.bookmypro.provider_service.common.dto.ServicePriceSummaryDto;
import com.bookmypro.provider_service.enums.Status;
import com.bookmypro.provider_service.model.ProviderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProviderServiceRepository extends JpaRepository<ProviderService, UUID> {

    List<ProviderService> findByProviderIdAndIsDeletedFalse(UUID providerId);

    Page<ProviderService> findByServiceIdAndStatusAndIsDeletedFalse(UUID serviceId, Status status, Pageable pageable);

    boolean existsByServiceIdAndStatusAndIsDeletedFalse(UUID serviceId, Status status);

    @Query("""
            SELECT new com.bookmypro.provider_service.common.dto.ServicePriceSummaryDto(
                ps.serviceId,
                COUNT(DISTINCT ps.providerId),
                MIN(ps.minimumPrice)
            )
            FROM ProviderService ps
            WHERE ps.serviceId IN :serviceIds
              AND ps.status = com.bookmypro.provider_service.enums.Status.ACTIVE
              AND ps.isDeleted = false
            GROUP BY ps.serviceId
        """)
    List<ServicePriceSummaryDto> findServicePriceSummaryByServiceIds(
            @Param("serviceIds") List<UUID> serviceIds
    );
}
