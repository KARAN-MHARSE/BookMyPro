package com.bookmypro.provider_service.common.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private UUID reviewId;
    private String customerName;
    private String customerImage;
    private Integer rating;
    private String review;
    private String reviewDate;
}
