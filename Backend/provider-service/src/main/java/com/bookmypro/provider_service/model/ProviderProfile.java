package com.bookmypro.provider_service.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bookmypro.provider_service.enums.Gender;
import com.bookmypro.provider_service.enums.Status;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "provider_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "provider_profile_id")
    private UUID providerProfileId;

    @Column(name = "provider_id", nullable=false)
    private UUID providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 20)
    private Gender gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "professional_title")
    private String professionalTitle;

    @Column(name = "provider_type")
    private String providerType;

    @Column(name = "professional_summary", columnDefinition = "TEXT")
    private String professionalSummary;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_status")
    private Status profileStatus;

    @Column(name = "service_radius")
    private Integer serviceRadius;

    @Column(name = "max_bookings")
    private Integer maxBookings;

    @Column(name = "home_service")
    private Boolean homeService;

    @Column(name = "emergency_service")
    private Boolean emergencyService;

    @Column(name = "weekend_available")
    private Boolean weekendAvailable;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    @CreationTimestamp
    private String createdBy;

    @Column(name = "updated_by")
    @UpdateTimestamp
    private String updatedBy;
}