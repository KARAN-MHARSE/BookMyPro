package com.bookmypro.identity_service.feature.auth.login;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Size(min = 8, max = 20)
	private String password;

    private String browser;

	private UUID deviceId;
	
    private String location;

	private String deviceName;

	private String ipAddress;

	private String userAgent;

	public String getLocation() {
		// TODO Auto-generated method stub
		return location;
	}

}
