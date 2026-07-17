package com.bookmypro.identity_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.bookmypro.identity_service.common.enums.CredentialStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credentials")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credential extends BaseEntity {

    @Id
    @Column(name = "credential_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID credentialId;

    private String username;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    private CredentialStatus status;

    @Column(name = "is_email_verified")
    private Boolean emailVerified;

    @Column(name = "is_phone_verified")
    private Boolean phoneVerified;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    private LocalDateTime lastLoginAt;

    private LocalDateTime lastPasswordChangeAt;

    private LocalDateTime passwordExpiryDate;

    private LocalDateTime accountLockedUntil;

    private Integer credentialVersion;
}
