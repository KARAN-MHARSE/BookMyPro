package com.bookmypro.MarketplaceService.common.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDetailsRequest {

    private List<UUID> serviceIds;

}
