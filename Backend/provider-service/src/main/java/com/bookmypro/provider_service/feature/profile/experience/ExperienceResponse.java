package com.bookmypro.provider_service.feature.profile.experience;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceResponse {
    private UUID experienceId;
    private UUID providerId;
    private String companyName;
    private String designation;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean currentlyWorking;
    private String description;
}
