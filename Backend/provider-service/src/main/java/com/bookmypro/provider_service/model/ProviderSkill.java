package com.bookmypro.provider_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "provider_skill")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "provider_skill_id")
    private UUID providerSkillId;

    @Column(name = "provider_id")
    private UUID providerId;

    @Column(name = "skill_id")
    private UUID skillId;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency_level")
    private com.bookmypro.provider_service.enums.ProficiencyLevel proficiencyLevel; // e.g. BEGINNER, INTERMEDIATE, EXPERT

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
