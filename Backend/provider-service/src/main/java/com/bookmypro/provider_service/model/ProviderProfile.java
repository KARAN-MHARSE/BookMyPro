package com.bookmypro.provider_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "provider_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "provider_profile_id")
    private UUID providerProfileId;

    @Column(name = "provider_id")
    private UUID providerId;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "provider_type_id")
    private UUID providerTypeId;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "business_description", columnDefinition = "TEXT")
    private String businessDescription;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "youtube_url")
    private String youtubeUrl;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "total_reviews")
    private Integer totalReviews;

    @Column(name = "total_completed_jobs")
    private Integer totalCompletedJobs;

    @Column(name = "profile_completion")
    private Integer profileCompletion;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private com.bookmypro.provider_service.enums.Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
