package com.bookmypro.identity_service.feature.auth.login;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private String message;
	private String accessToken;
	private String credentialId;
	private String deviceId;
	private List<String> roles;

}
