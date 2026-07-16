package com.bookmypro.provider_service.feature.profile.education;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationRequest {
    private UUID educationId;
    private UUID credentialId;

    @NotBlank(message = "Institution name is required")
    private String institutionName;

    @NotBlank(message = "Degree is required")
    private String degree;

    private String specialization;
    private String startYear;
    private String endYear;
    private Boolean currentlyStudying;
    private String description;
}
