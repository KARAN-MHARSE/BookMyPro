package com.bookmypro.identity_service.feature.auth.Logout;

import java.util.UUID;

import lombok.Data;

@Data
public class LogoutRequest {

    private String refreshToken;

    private UUID deviceId;
}
