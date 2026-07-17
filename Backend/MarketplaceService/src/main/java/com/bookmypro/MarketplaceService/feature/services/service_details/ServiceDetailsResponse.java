package com.bookmypro.MarketplaceService.feature.services.service_details;

import java.util.List;

import com.bookmypro.MarketplaceService.common.dto.ProviderDto;
import com.bookmypro.MarketplaceService.common.dto.ReviewDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDetailsResponse {

	private ServiceInfo service;
	private List<ProviderDto> providers;
	private List<ReviewDto> reviews;
	private List<FaqInfo> faqs;
	private List<GalleryInfo> gallery;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ServiceInfo {
		private String serviceId;
		private String serviceName;
		private String slug;
		private String categoryId;
		private String categoryName;
		private String description;
		private String thumbnailUrl;
		private String bannerUrl;
		private Double averageRating;
		private Long totalReviews;
		private Long providerCount;
		private Integer startingPrice;
		private Integer estimatedDuration;
		private List<String> includedItems;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FaqInfo {
		private String faqId;
		private String question;
		private String answer;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class GalleryInfo {
		private String imageId;
		private String imageUrl;
	}
}
