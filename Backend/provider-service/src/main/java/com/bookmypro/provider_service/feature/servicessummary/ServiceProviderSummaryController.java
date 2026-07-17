package com.bookmypro.provider_service.feature.servicessummary;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceProviderSummaryController {
	private final ServiceProviderSummaryService service;
	
	
	
	@PostMapping("/summary")
	ResponseEntity<List<ServiceProviderSummaryResponse>> getServicesProviderSummary(@RequestBody ServiceProviderSummaryRequest request){
		List<ServiceProviderSummaryResponse> response = service.getServicesProviderSummary(request);
		return ResponseEntity.ok(response);
	}

}
