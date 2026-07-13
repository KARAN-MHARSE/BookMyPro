package com.bookmypro.master_service.provider;

import java.util.List;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.getLookUps.LookupCriteria;
import com.bookmypro.master_service.feature.getLookUps.LookupDto;

public interface LookupProvider {

	LookupType getType();

	List<LookupDto> getLookups(LookupCriteria criteria);

}