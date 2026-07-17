package com.bookmypro.provider_service.feature.profile.professionalinfo;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalInfoRequest {

    @NotBlank(message = "Professional title is required")
    private String professionalTitle;

    @NotBlank(message = "Provider type is required")
    private String providerType;

    @NotNull(message = "Experience is required")
    private Integer experience;

    private String businessName;
    private LocalDate workingSince;
    private String gstNumber;
    private String panNumber;
    private String website;
    private String portfolio;
    private String professionalSummary;
}
