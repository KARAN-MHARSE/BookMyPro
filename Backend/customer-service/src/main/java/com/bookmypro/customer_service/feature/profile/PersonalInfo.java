package com.bookmypro.customer_service.feature.profile;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {
	private String firstName;
	private String middleName;
	private String lastName;
	private String dob;
	private String gender;
	private String language;
	private String occupation;
	private byte[] profilePhoto;
	private String avatarUrl;
	private String bio;
}
