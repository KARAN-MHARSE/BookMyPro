package com.bookmypro.provider_service.feature.providerdetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceOfferedDto {

    private String title;
    private String duration;
    private Integer price;
    private Boolean includesSupplies;
}
