package com.bookmypro.MarketplaceService.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private String reviewId;
    private String customerName;
    private String customerImage;
    private Double rating;
    private String review;
    private String reviewDate;
}
