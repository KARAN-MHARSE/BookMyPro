package com.bookmypro.provider_service.feature.servicessummary;

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
public class ServiceProviderSummaryResponse {
	private UUID serviceId;
	private Double averageRating;
	private Long totalReviews;
	private Long providerCount;
	private BigDecimal startingPrice;
}
