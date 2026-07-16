package com.bookmypro.provider_service.feature.profile.personal_info;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProfileRequest {
	private String profilePhoto;
	@NotBlank
	private String firstName;

	private String middleName;

	@NotBlank
	private String lastName;
	private String dob;
	private String gender;
	private String language;

	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be a valid 10-digit Indian mobile number")
	private String phoneNumber;
	private String bio;
}