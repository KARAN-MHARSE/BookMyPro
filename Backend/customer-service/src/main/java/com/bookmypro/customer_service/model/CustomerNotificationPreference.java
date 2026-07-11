package com.bookmypro.customer_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_notification_preference")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerNotificationPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID  id;

    private Long customerId;

    private Boolean bookingNotification;

    private Boolean paymentNotification;

    private Boolean promotionNotification;

    private Boolean reminderNotification;

    private Boolean reviewNotification;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}