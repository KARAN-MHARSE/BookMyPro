package com.bookmypro.MarketplaceService.feature.services.services_grid;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceSummaryResponse {
	private UUID serviceId;
	private String serviceName;
	private String slug;
	private String serviceCategoryName;
	private String serviceThumbnailUrl;
	private Long averageRating;
	private Long totalReviews;
	private Long providerCount;
	private BigDecimal startingPrice;
	private Integer estimatedDuration;
}
