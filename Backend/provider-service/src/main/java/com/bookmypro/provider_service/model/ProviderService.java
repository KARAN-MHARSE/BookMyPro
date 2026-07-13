package com.bookmypro.provider_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "provider_service")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "provider_service_id")
    private UUID providerServiceId;

    @Column(name = "provider_id")
    private UUID providerId;

    @Column(name = "service_id")
    private UUID serviceId;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_type")
    private com.bookmypro.provider_service.enums.PriceType priceType; // HOURLY/FIXED/VISIT

    @Column(name = "minimum_price")
    private BigDecimal minimumPrice;

    @Column(name = "maximum_price")
    private BigDecimal maximumPrice;

    @Column(name = "estimated_duration")
    private Integer estimatedDuration; // in minutes

    @Column(name = "currency")
    private String currency; // e.g. INR, USD

    @Column(name = "home_service")
    private Boolean homeService;

    @Column(name = "emergency_service")
    private Boolean emergencyService;

    @Column(name = "weekend_available")
    private Boolean weekendAvailable;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private com.bookmypro.provider_service.enums.Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
