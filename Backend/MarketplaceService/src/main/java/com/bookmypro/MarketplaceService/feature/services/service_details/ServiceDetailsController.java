package com.bookmypro.MarketplaceService.feature.services.service_details;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/service/details")
@RequiredArgsConstructor
public class ServiceDetailsController {
	private final ServiceDetailsService service;
	
	
	@GetMapping("/{serviceId}")
	public ResponseEntity<ServiceDetailsResponse> getServiceDetails(@PathVariable UUID serviceId){
		ServiceDetailsResponse response = service.getServiceDetails(serviceId);
		return ResponseEntity.ok(response);
	}

}
