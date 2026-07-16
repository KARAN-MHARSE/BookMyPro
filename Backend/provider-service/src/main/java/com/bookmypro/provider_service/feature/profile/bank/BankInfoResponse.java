package com.bookmypro.provider_service.feature.profile.bank;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankInfoResponse {
    private UUID bankId;
    private UUID providerId;
    private String accountHolderName;
    private String bankName;
    private String accountNumber;
    private String confirmAccountNumber; // to populateconfirm input on frontend
    private String ifscCode;
    private String branchName;
    private String accountType; // SAVINGS/CURRENT
    private String upiId;
    private String panNumber;
    private String gstNumber;
    private Boolean isPrimary;
    private String verificationStatus;
}
