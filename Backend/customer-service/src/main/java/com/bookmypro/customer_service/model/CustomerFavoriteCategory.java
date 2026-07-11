package com.bookmypro.customer_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_favorite_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerFavoriteCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long customerId;

    private Long categoryId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}