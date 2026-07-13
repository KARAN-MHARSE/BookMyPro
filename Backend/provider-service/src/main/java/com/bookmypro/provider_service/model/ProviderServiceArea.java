package com.bookmypro.provider_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "provider_service_area")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderServiceArea {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "service_area_id")
    private UUID serviceAreaId;

    @Column(name = "provider_id")
    private UUID providerId;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "radius_km")
    private Double radiusKm;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
