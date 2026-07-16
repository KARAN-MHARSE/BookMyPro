package com.bookmypro.provider_service.feature.profile.experience;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceRequest {
    private UUID experienceId;
    private UUID credentialId;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;
    private Boolean currentlyWorking;
    private String description;
}
