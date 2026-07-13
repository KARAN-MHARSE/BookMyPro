package com.bookmypro.customer_service.common.service.downstream;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.bookmypro.customer_service.common.dto.LookupDto;
import com.bookmypro.customer_service.common.request.LookupRequest;



@HttpExchange
public interface MasterDownStreamService {
	
	@PostExchange("/lookups")
    public ResponseEntity<Map<String, List<LookupDto>>> getLookups(
            @RequestBody LookupRequest request);

}
