package com.bookmypro.provider_service.common.service.downstream;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.bookmypro.provider_service.common.dto.LookupDto;
import com.bookmypro.provider_service.common.request.LookupRequest;
import com.bookmypro.provider_service.common.dto.MasterDetailsDto;
import com.bookmypro.provider_service.common.dto.MasterDetailsRequest;
import com.bookmypro.provider_service.enums.LookupType;

@HttpExchange
public interface MasterDownStreamService {
	
	@PostExchange("/lookups")
    public ResponseEntity<Map<String, List<LookupDto>>> getLookups(
            @RequestBody LookupRequest request);

	@PostExchange("/masters/details")
	public ResponseEntity<Map<LookupType, List<MasterDetailsDto>>> getMasterDetails(
			@RequestBody MasterDetailsRequest request);

}
