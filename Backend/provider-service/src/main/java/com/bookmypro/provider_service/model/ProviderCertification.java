package com.bookmypro.provider_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "provider_certification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderCertification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "certification_id")
    private UUID certificationId;

    @Column(name = "provider_id")
    private UUID providerId;

    @Column(name = "certificate_name")
    private String certificateName;

    @Column(name = "issued_by")
    private String issuedBy;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "certificate_url")
    private String certificateUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status")
    private com.bookmypro.provider_service.enums.VerificationStatus verificationStatus; // e.g. PENDING, VERIFIED, REJECTED
}
