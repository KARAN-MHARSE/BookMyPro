package com.bookmypro.customer_service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_statistics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long customerId;

    private Integer totalBookings;

    private Integer completedBookings;

    private Integer cancelledBookings;

    private BigDecimal totalSpent;

    private BigDecimal averageRatingGiven;

    private LocalDateTime lastBookingAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
