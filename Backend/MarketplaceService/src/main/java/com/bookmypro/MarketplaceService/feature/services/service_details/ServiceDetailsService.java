package com.bookmypro.MarketplaceService.feature.services.service_details;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmypro.MarketplaceService.common.dto.ProviderDto;
import com.bookmypro.MarketplaceService.common.dto.ReviewDto;
import com.bookmypro.MarketplaceService.common.dto.ServiceDetailsDto;
import com.bookmypro.MarketplaceService.common.request.ServiceProviderSummaryRequest;
import com.bookmypro.MarketplaceService.common.response.ServiceProviderSummaryResponse;
import com.bookmypro.MarketplaceService.common.services.downstream.MasterDownStreamService;
import com.bookmypro.MarketplaceService.common.services.downstream.ProviderDownStreamService;
import com.bookmypro.MarketplaceService.feature.services.service_details.ServiceDetailsResponse.FaqInfo;
import com.bookmypro.MarketplaceService.feature.services.service_details.ServiceDetailsResponse.GalleryInfo;
import com.bookmypro.MarketplaceService.feature.services.service_details.ServiceDetailsResponse.ServiceInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceDetailsService {

	private final ProviderDownStreamService providerDownStreamService;
	private final MasterDownStreamService masterDownStreamService;

	public ServiceDetailsResponse getServiceDetails(UUID serviceId) {
		if (serviceId == null) {
			throw new IllegalArgumentException("serviceId is required");
		}

		ServiceDetailsDto masterService = fetchMasterService(serviceId);
		ServiceProviderSummaryResponse providerSummary = fetchProviderSummary(serviceId);

		ServiceDetailsResponse response = new ServiceDetailsResponse();
		response.setService(buildServiceInfo(masterService, providerSummary));
		response.setProviders(fetchProviders(serviceId));
		response.setReviews(fetchReviews(serviceId));
		response.setFaqs(getHardcodedFaqs());
		response.setGallery(getHardcodedGallery());
		return response;
	}

	private ServiceDetailsDto fetchMasterService(UUID serviceId) {
		ResponseEntity<List<ServiceDetailsDto>> servicesResponse = masterDownStreamService.getAllServicesDetails();

		return Optional.ofNullable(servicesResponse.getBody())
				.orElse(Collections.emptyList())
				.stream()
				.filter(service -> serviceId.equals(service.getServiceId()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Service not found: " + serviceId));
	}

	private ServiceProviderSummaryResponse fetchProviderSummary(UUID serviceId) {
		ServiceProviderSummaryRequest request = new ServiceProviderSummaryRequest(List.of(serviceId));
		ResponseEntity<List<ServiceProviderSummaryResponse>> summaryResponse = providerDownStreamService
				.getServicesProviderSummary(request);

		return Optional.ofNullable(summaryResponse.getBody())
				.orElse(Collections.emptyList())
				.stream()
				.filter(summary -> serviceId.equals(summary.getServiceId()))
				.findFirst()
				.orElse(null);
	}

	private ServiceInfo buildServiceInfo(ServiceDetailsDto masterService, ServiceProviderSummaryResponse providerSummary) {
		String thumbnailUrl = masterService.getServiceThumbnailUrl();

		return ServiceInfo.builder()
				.serviceId(masterService.getServiceId().toString())
				.serviceName(masterService.getServiceName())
				.slug(toSlug(masterService.getServiceName()))
				.categoryId("")
				.categoryName(masterService.getServiceCategoryName())
				.description("")
				.thumbnailUrl(thumbnailUrl)
				.bannerUrl(thumbnailUrl)
				.averageRating(providerSummary != null && providerSummary.getAverageRating() != null
						? providerSummary.getAverageRating().doubleValue()
						: 0.0)
				.totalReviews(providerSummary != null && providerSummary.getTotalReviews() != null
						? providerSummary.getTotalReviews()
						: 0L)
				.providerCount(providerSummary != null && providerSummary.getProviderCount() != null
						? providerSummary.getProviderCount()
						: 0L)
				.startingPrice(providerSummary != null ? toInteger(providerSummary.getStartingPrice()) : 0)
				.estimatedDuration(masterService.getEstimatedDuration())
				.includedItems(List.of())
				.build();
	}

	private List<ProviderDto> fetchProviders(UUID serviceId) {
		ResponseEntity<List<ProviderDto>> providersResponse = providerDownStreamService.getServiceProviders(serviceId, 0, 10);

		return Optional.ofNullable(providersResponse)
				.map(ResponseEntity::getBody)
				.orElse(Collections.emptyList());
	}

	private List<ReviewDto> fetchReviews(UUID serviceId) {
		ResponseEntity<List<ReviewDto>> reviewsResponse = providerDownStreamService.getReviews(serviceId, 0, 10);

		return Optional.ofNullable(reviewsResponse)
				.map(ResponseEntity::getBody)
				.orElse(Collections.emptyList());
	}

	private List<FaqInfo> getHardcodedFaqs() {
		return List.of(
				FaqInfo.builder()
						.faqId("F001")
						.question("How long does the service take?")
						.answer("Usually between 90 and 120 minutes.")
						.build(),
				FaqInfo.builder()
						.faqId("F002")
						.question("Do professionals bring cleaning supplies?")
						.answer("Yes. All required equipment and supplies are included.")
						.build(),
				FaqInfo.builder()
						.faqId("F003")
						.question("Can I cancel my booking?")
						.answer("Yes. Free cancellation is available up to 12 hours before the appointment.")
						.build());
	}

	private List<GalleryInfo> getHardcodedGallery() {
		return List.of(
				GalleryInfo.builder()
						.imageId("G001")
						.imageUrl("assets/images/gallery/gallery1.jpg")
						.build(),
				GalleryInfo.builder()
						.imageId("G002")
						.imageUrl("assets/images/gallery/gallery2.jpg")
						.build(),
				GalleryInfo.builder()
						.imageId("G003")
						.imageUrl("assets/images/gallery/gallery3.jpg")
						.build(),
				GalleryInfo.builder()
						.imageId("G004")
						.imageUrl("assets/images/gallery/gallery4.jpg")
						.build());
	}

	private String toSlug(String serviceName) {
		if (serviceName == null || serviceName.isBlank()) {
			return "";
		}
		return serviceName.trim().toLowerCase().replaceAll("[^a-z0-9\\s-]", "").replaceAll("\\s+", "-");
	}

	private Integer toInteger(BigDecimal value) {
		if (value == null) {
			return 0;
		}
		return value.intValue();
	}
}
