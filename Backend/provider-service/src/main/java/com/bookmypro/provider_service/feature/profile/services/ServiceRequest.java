package com.bookmypro.provider_service.feature.profile.services;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {
    private UUID providerServiceId;
    private UUID credentialId;

    @NotNull(message = "Service selection is required")
    private UUID serviceId;

    @NotNull(message = "Category selection is required")
    private UUID categoryId;

    private Integer experienceYears;

    @NotNull(message = "Base price is required")
    private BigDecimal basePrice;

    @NotNull(message = "Price type is required")
    private String priceType; // HOURLY, FIXED, VISIT

    private BigDecimal minimumPrice;
    private BigDecimal maximumPrice;

    @NotNull(message = "Estimated duration is required")
    private Integer estimatedDuration; // in minutes

    private Boolean homeService;
    private Boolean emergencyService;
    private Boolean weekendAvailable;
    private Boolean active;
}
