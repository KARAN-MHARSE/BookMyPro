package com.bookmypro.MarketplaceService.feature.services.services_grid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServicesGridController {
	private final ServicesGridService service;
	
	@GetMapping("/summary")
	public ResponseEntity<List<ServiceSummaryResponse>> getServicesGridList() {
		List<ServiceSummaryResponse> respons = service.getServicesGridList();
		
		return ResponseEntity.ok(respons);

	}
	

}
