package com.bookmypro.provider_service.common.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.bookmypro.provider_service.enums.LookupType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterDetailsRequest {
    private Map<LookupType, List<UUID>> masterIds;
}
