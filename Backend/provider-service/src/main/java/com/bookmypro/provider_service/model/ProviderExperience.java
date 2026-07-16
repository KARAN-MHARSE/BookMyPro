package com.bookmypro.provider_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "provider_experience")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "experience_id")
    private UUID experienceId;

    @Column(name = "provider_id")
    private UUID providerId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "designation")
    private String designation;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "currently_working")
    private Boolean currentlyWorking;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
