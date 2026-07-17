package com.bookmypro.MarketplaceService.common.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceDetailsDto {

    private UUID serviceId;

    private String serviceName;

    private Integer estimatedDuration;

    private String serviceCategoryName;

    private String serviceThumbnailUrl;

}
