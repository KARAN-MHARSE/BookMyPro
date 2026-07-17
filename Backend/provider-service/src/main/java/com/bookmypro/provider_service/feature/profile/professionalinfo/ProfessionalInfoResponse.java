package com.bookmypro.provider_service.feature.profile.professionalinfo;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalInfoResponse {
    private UUID providerId;
    private UUID credentialId;

    private String professionalTitle;
    private String providerType;
    private Integer experience;

    private String businessName;
    private LocalDate workingSince;
    private String gstNumber;
    private String panNumber;
    private String website;
    private String portfolio;
    private String professionalSummary;
}
