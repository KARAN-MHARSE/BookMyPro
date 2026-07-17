package com.bookmypro.MarketplaceService.common.services.downstream;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.bookmypro.MarketplaceService.common.dto.ProviderDto;
import com.bookmypro.MarketplaceService.common.dto.ReviewDto;
import com.bookmypro.MarketplaceService.common.request.ServiceProviderSummaryRequest;
import com.bookmypro.MarketplaceService.common.response.ServiceProviderSummaryResponse;

@HttpExchange()
public interface ProviderDownStreamService {

	@PostExchange("/services/summary")
	ResponseEntity<List<ServiceProviderSummaryResponse>> getServicesProviderSummary(
			@RequestBody ServiceProviderSummaryRequest request);

	@GetExchange("/service/providers")
	ResponseEntity<List<ProviderDto>> getServiceProviders(
			@RequestParam UUID serviceID,
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize);

	@GetExchange("/service/reviews")
	ResponseEntity<List<ReviewDto>> getReviews(
			@RequestParam UUID serviceID,
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize);
}
