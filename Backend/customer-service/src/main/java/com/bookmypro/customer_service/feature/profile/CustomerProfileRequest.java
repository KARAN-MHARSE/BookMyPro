package com.bookmypro.customer_service.feature.profile;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.bookmypro.customer_service.common.request.Address;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfileRequest {
	@NotBlank
	private UUID credentialId;
	@NotBlank
	private String firstName;
	private String middleName;
	@NotBlank
	private String lastName;
	private String dob;
	private String gender;
	private String language;
	private String occupation;
	private MultipartFile profilePhoto;
	private String avatarUrl;
	private String bio;
	private UUID defaultAddressId;

}
