package com.bookmypro.provider_service.feature.providerdetails;

import java.util.List;
import java.util.UUID;

import com.bookmypro.provider_service.common.dto.AvailabilityDto;
import com.bookmypro.provider_service.common.dto.ReviewDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderDetailsResponse {

    private UUID id;
    private String name;
    private String initials;
    private Boolean isVerified;
    private Boolean isTopRated;
    private String specialty;
    private String location;
    private Integer experienceYears;
    private Double rating;
    private Integer reviewCount;
    private Integer completedJobs;
    private String responseTime;
    private String about;

    private List<String> skills;
    private List<ServiceOfferedDto> servicesOffered;
    private List<String> certifications;
    private List<String> portfolioGradients;
    private List<ReviewDto> reviews;
    private List<AvailabilityDto> availability;

    private String workingHours;
    private Integer startingPrice;
}
