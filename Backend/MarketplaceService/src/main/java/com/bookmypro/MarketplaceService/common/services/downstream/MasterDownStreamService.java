package com.bookmypro.MarketplaceService.common.services.downstream;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.bookmypro.MarketplaceService.common.dto.LookupDto;
import com.bookmypro.MarketplaceService.common.dto.ServiceDetailsDto;
import com.bookmypro.MarketplaceService.common.request.LookupRequest;

@HttpExchange
public interface MasterDownStreamService {

	@PostExchange("/lookups")
	public ResponseEntity<Map<String, List<LookupDto>>> getLookups(@RequestBody LookupRequest request);

	@GetExchange("/services/summary")
	public ResponseEntity<List<ServiceDetailsDto>> getAllServicesDetails();
}
