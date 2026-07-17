package com.bookmypro.master_service.feature.getlookups;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LookupRequest {

    private List<LookupCriteria> lookups;

}
