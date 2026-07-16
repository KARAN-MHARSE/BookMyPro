package com.bookmypro.MarketplaceService.common.request;

import java.util.List;

import com.bookmypro.MarketplaceService.common.dto.LookupCriteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LookupRequest {

    private List<LookupCriteria> lookups;

}
