package com.bookmypro.identity_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "session")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID sessionId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credential_id")
    private Credential credential;


    private String jwtId;

    private String device;

    private String browser;

    private String location;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime lastAccessed;

    private LocalDateTime expiresAt;
}
