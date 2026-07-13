package com.bookmypro.provider_service.common.dto;

import java.util.UUID;

import com.bookmypro.provider_service.enums.LookupType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LookupCriteria {
	private LookupType type;

	private UUID parentId;
}
