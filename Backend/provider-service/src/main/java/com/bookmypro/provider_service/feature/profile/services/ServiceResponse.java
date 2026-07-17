package com.bookmypro.provider_service.feature.profile.services;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bookmypro.provider_service.common.dto.LookupDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {
	private List<ServiceDto> services;
	private Map<String, List<LookupDto>> lookups;
}
