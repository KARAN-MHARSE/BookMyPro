package com.bookmypro.customer_service.feature.onboarding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOnboardingRequest {
	@NotBlank(message = "First name is required")
	@Size(max = 50, message = "First name cannot exceed 50 characters")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(max = 50, message = "Last name cannot exceed 50 characters")
	private String lastName;
	
//	@NotBlank(message = "Username is required")
	private String userName;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be a valid 10-digit Indian mobile number")
	private String phoneNumber;

	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
	private String password;
}
