package com.bookmypro.master_service.feature.getlookups;

import java.util.UUID;

import com.bookmypro.master_service.enums.LookupType;

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
