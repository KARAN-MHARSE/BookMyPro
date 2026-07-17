package com.bookmypro.provider_service.feature.servicessummary;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookmypro.provider_service.common.dto.ServicePriceSummaryDto;
import com.bookmypro.provider_service.common.dto.ServiceRatingSummaryDto;
import com.bookmypro.provider_service.repository.ProviderServiceRepository;
import com.bookmypro.provider_service.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceProviderSummaryService {
	private final ProviderServiceRepository providerServiceRepository;
	private final ReviewRepository reviewRepository;

	public List<ServiceProviderSummaryResponse> getServicesProviderSummary(ServiceProviderSummaryRequest request) {

		List<ServicePriceSummaryDto> priceSummaries = providerServiceRepository
				.findServicePriceSummaryByServiceIds(request.getServicesIds());

		List<ServiceRatingSummaryDto> ratingSummaries = reviewRepository
				.findServiceRatingSummary(request.getServicesIds());

		Map<UUID, ServiceRatingSummaryDto> ratingMap = ratingSummaries.stream()
				.collect(Collectors.toMap(ServiceRatingSummaryDto::getServiceId, Function.identity()));

		return priceSummaries.stream().map(price -> {
			ServiceProviderSummaryResponse.ServiceProviderSummaryResponseBuilder builder = ServiceProviderSummaryResponse
					.builder();

			builder.serviceId(price.getServiceId());
			builder.providerCount(price.getProviderCount());
			builder.startingPrice(price.getMinimumPrice());

			ServiceRatingSummaryDto rating = ratingMap.get(price.getServiceId());

			if (rating != null) {
				builder.averageRating(rating.getAverageRating());
				builder.totalReviews(rating.getTotalReviews());
			} else {

				builder.averageRating(0.0);
				builder.totalReviews(0L);
			}

			return builder.build();
		}).toList();
	}

}
