package com.bookmypro.identity_service.feature.auth.forgotPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordOtpVerifiedRequest {
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 8,max  =20)
	private String password;
	
	@NotBlank
	private String otp;

}
