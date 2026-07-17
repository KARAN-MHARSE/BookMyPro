package com.bookmypro.MarketplaceService.feature.services.services_grid;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmypro.MarketplaceService.common.dto.ServiceDetailsDto;
import com.bookmypro.MarketplaceService.common.request.ServiceProviderSummaryRequest;
import com.bookmypro.MarketplaceService.common.response.ServiceProviderSummaryResponse;
import com.bookmypro.MarketplaceService.common.services.downstream.MasterDownStreamService;
import com.bookmypro.MarketplaceService.common.services.downstream.ProviderDownStreamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicesGridService {
	private final ProviderDownStreamService providerDownStreamService;
	private final MasterDownStreamService masterDownStreamService;

	public List<ServiceSummaryResponse> getServicesGridList() {

		ResponseEntity<List<ServiceDetailsDto>> servicesResponse = masterDownStreamService.getAllServicesDetails();

		List<ServiceDetailsDto> serviceList =
		        Optional.ofNullable(servicesResponse.getBody())
		                .orElse(Collections.emptyList());

		if (serviceList.isEmpty()) {
			return Collections.emptyList();
		}

		List<UUID> serviceIds = serviceList.stream().map(ServiceDetailsDto::getServiceId).toList();

		ServiceProviderSummaryRequest request = new ServiceProviderSummaryRequest(serviceIds);

		ResponseEntity<List<ServiceProviderSummaryResponse>> providerResponse = providerDownStreamService
				.getServicesProviderSummary(request);

		Map<UUID, ServiceProviderSummaryResponse> providerSummaryMap = Optional.ofNullable(providerResponse.getBody())
				.orElse(Collections.emptyList()).stream()
				.collect(Collectors.toMap(ServiceProviderSummaryResponse::getServiceId, Function.identity()));

		return serviceList.stream()
				.map(ss->{
					ServiceProviderSummaryResponse ps = providerSummaryMap.get(ss.getServiceId());
					
					return ServiceSummaryResponse.builder()
							.serviceId(ss.getServiceId())
	                        .serviceName(ss.getServiceName())
	                        .slug("")
	                        .serviceCategoryName(ss.getServiceCategoryName())
	                        .serviceThumbnailUrl(ss.getServiceThumbnailUrl())
	                        .averageRating(ps != null ? ps.getAverageRating() : 0L)
	                        .totalReviews(ps != null ? ps.getTotalReviews() : 0L)
	                        .providerCount(ps != null ? ps.getProviderCount() : 0L)
	                        .startingPrice(ps != null ? ps.getStartingPrice() : BigDecimal.ZERO)
	                        .estimatedDuration(ss.getEstimatedDuration())
	                        .build();
					
					
				})
				.collect(Collectors.toList());
	}
}
