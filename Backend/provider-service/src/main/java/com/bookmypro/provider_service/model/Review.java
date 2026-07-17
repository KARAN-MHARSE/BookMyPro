package com.bookmypro.provider_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.bookmypro.provider_service.enums.ReviewStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reviewId;

    private UUID bookingId;

    private UUID providerId;

    private UUID serviceId;

    private UUID customerId;

    @Min(1)
    @Max(5)
    private Integer rating;

    @Column(length = 100)
    private String reviewTitle;

    @Column(length = 2000)
    private String reviewMessage;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;
    
    @Column(name = "created_at")
	protected LocalDateTime createdAt;

	@Column(name = "updated_at")
	protected LocalDateTime updatedAt;

	@Column(name = "created_by")
	protected String createdBy;

	@Column(name = "updated_by")
	protected String updatedBy;
}
