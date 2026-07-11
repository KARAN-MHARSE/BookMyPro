package com.bookmypro.customer_service.model;


import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_preference")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Long customerId;

    private String preferredLanguage;

    private String currency;

    private Boolean allowMarketing;

    private Boolean allowPush;

    private Boolean allowEmail;

    private Boolean allowSms;

    private Boolean darkMode;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
