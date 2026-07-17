package com.bookmypro.master_service.feature.genericdetails;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterDetailsDto {
    private UUID masterId;
    private String desc;
}
