package com.bookmypro.provider_service.feature.servicessummary;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderSummaryRequest {
	private List<UUID> servicesIds;

}
