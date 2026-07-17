package com.bookmypro.provider_service.common.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicePriceSummaryDto {
	private UUID serviceId;
	private Long providerCount;
	private BigDecimal minimumPrice;
}
