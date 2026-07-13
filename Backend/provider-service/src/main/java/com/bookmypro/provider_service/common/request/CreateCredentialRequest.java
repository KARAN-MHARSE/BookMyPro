package com.bookmypro.provider_service.common.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCredentialRequest {

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

//	@NotBlank(message = "Username is required")
	private String userName;
	
	@NotBlank(message = "roleId is required")
	private String roleCode;

	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	private String password;
}
