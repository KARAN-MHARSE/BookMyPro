package com.bookmypro.customer_service.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.bookmypro.customer_service.enums.WalletStatus;

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
@Table(name = "customer_wallet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long customerId;

    private BigDecimal availableBalance;

    private BigDecimal blockedBalance;

    private BigDecimal lifetimeCashback;

    @Enumerated(EnumType.STRING)
    private WalletStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
