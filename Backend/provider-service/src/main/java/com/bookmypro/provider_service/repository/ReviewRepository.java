package com.bookmypro.provider_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookmypro.provider_service.common.dto.ServiceRatingSummaryDto;
import com.bookmypro.provider_service.enums.ReviewStatus;
import com.bookmypro.provider_service.model.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

	@Query("""
			    SELECT new com.bookmypro.provider_service.common.dto.ServiceRatingSummaryDto(
			        r.serviceId,
			        AVG(r.rating),
			        COUNT(r.reviewId)
			    )
			    FROM Review r
			    WHERE r.serviceId IN :serviceIds
			      AND r.status = com.bookmypro.provider_service.enums.ReviewStatus.PUBLISHED
			    GROUP BY r.serviceId
			""")
	List<ServiceRatingSummaryDto> findServiceRatingSummary(@Param("serviceIds") List<UUID> serviceIds);

	List<Review> findByServiceIdAndProviderIdInAndStatus(UUID serviceId, List<UUID> providerIds, ReviewStatus status);

	@Query("""
			SELECT r FROM Review r
			WHERE r.status = :status
			  AND (:serviceId IS NULL OR r.serviceId = :serviceId)
			  AND (:providerId IS NULL OR r.providerId = :providerId)
			ORDER BY r.createdAt DESC
			""")
	Page<Review> findPublishedReviews(
			@Param("serviceId") UUID serviceId,
			@Param("providerId") UUID providerId,
			@Param("status") ReviewStatus status,
			Pageable pageable);
}
