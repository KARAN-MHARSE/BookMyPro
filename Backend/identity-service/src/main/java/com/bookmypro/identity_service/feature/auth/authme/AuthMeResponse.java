package com.bookmypro.identity_service.feature.auth.authme;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthMeResponse {
	private String email;
	private String credentialId;
	private List<String> roles;
}
