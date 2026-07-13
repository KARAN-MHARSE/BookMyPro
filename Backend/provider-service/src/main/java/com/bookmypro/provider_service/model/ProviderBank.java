package com.bookmypro.provider_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "provider_bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderBank {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bank_id")
    private UUID bankId;

    @Column(name = "provider_id")
    private UUID providerId;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "branch_name")
    private String branchName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private com.bookmypro.provider_service.enums.AccountType accountType; // SAVINGS/CURRENT

    @Column(name = "upi_id")
    private String upiId;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status")
    private com.bookmypro.provider_service.enums.VerificationStatus verificationStatus; // e.g. PENDING, VERIFIED, REJECTED

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
    
    @Column(name = "status")
    private LocalDateTime status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public String getMaskedAccountNumber() {
        if (accountNumber == null || accountNumber.isEmpty()) {
            return "";
        }
        if (accountNumber.length() <= 4) {
            return "****";
        }
        return "XXXX XXXX " + accountNumber.substring(accountNumber.length() - 4);
    }
}
