package com.bookmypro.provider_service.feature.profile.services;

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
public class ServiceDto {
	private UUID providerServiceId;
    private UUID providerId;
    private UUID serviceId;
    private UUID categoryId;
    private Integer experienceYears;
    private BigDecimal basePrice;
    private String priceType;
    private BigDecimal minimumPrice;
    private BigDecimal maximumPrice;
    private Integer estimatedDuration;
    private Boolean homeService;
    private Boolean emergencyService;
    private Boolean weekendAvailable;
    private String status;
    private Boolean active;
    
}
