package com.bookmypro.identity_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.bookmypro.identity_service.common.enums.OtpPurpose;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "otp")
@Getter
@Setter
@Builder
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID otpId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credential_id")
    private Credential credential;

    @Enumerated(EnumType.STRING)
    private OtpPurpose purpose;

    private String otpCode;

    private LocalDateTime expiresAt;

    private Boolean verified;

    private Integer attemptCount;

    private LocalDateTime createdAt;
}