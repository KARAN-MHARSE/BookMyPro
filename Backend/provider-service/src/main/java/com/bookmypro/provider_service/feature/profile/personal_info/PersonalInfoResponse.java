package com.bookmypro.provider_service.feature.profile.personal_info;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoResponse {
    private UUID providerId;
    private UUID credentialId;
    private String profilePhoto;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private String gender;
    private String language;
    private String phoneNumber;
    private String bio;
}
