package com.bookmypro.provider_service.feature.profile.education;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponse {
    private UUID educationId;
    private UUID providerId;
    private String institutionName;
    private String degree;
    private String specialization;
    private String startYear;
    private String endYear;
    private Boolean currentlyStudying;
    private String description;
}
