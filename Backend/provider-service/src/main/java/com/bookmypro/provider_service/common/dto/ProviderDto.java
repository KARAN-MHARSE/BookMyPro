package com.bookmypro.provider_service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDto {

    private String providerId;
    private String fullName;
    private String profileImage;
    private Double averageRating;
    private Integer totalReviews;
    private Integer experienceYears;
    private Integer completedJobs;
    private Integer distance;
    private String nextAvailableSlot;
    private Integer minimumPrice;
    private Boolean verified;
}
