package com.bookmypro.master_service.provider;

import java.util.List;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.getlookups.LookupCriteria;
import com.bookmypro.master_service.feature.getlookups.LookupDto;
import com.bookmypro.master_service.feature.genericdetails.MasterDetailsDto;
import java.util.UUID;

public interface LookupProvider {

	LookupType getType();

	List<LookupDto> getLookups(LookupCriteria criteria);

	List<MasterDetailsDto> getDetailsByIds(List<UUID> ids);

}
