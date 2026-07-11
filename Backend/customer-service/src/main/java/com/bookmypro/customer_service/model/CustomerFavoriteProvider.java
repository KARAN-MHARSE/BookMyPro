package com.bookmypro.customer_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_favorite_provider")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerFavoriteProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long customerId;

    private Long providerId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}