package com.bookmypro.identity_service.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@Builder
public class RefreshToken {

    @Id
    private UUID tokenId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credential_id")
    private Credential credential;


    @Column(columnDefinition = "TEXT")
    private String token;


    private LocalDate expiresAt;

    private Boolean revoked;

    private String deviceId;

    private String deviceName;

    private String ipAddress;

    private String userAgent;

    private LocalDateTime createdAt;
}