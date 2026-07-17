package com.bookmypro.provider_service.common.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRatingSummaryDto {
	private UUID serviceId;
    private Double averageRating;
    private Long totalReviews;
}
