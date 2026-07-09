package com.bookmypro.identity_service.feature.auth.Logout;

import lombok.Data;

@Data
public class LogoutRequest {

    private String refreshToken;

    private String deviceId;
}
