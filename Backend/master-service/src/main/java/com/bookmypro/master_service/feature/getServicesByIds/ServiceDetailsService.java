package com.bookmypro.master_service.feature.getservicesbyids;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import com.bookmypro.master_service.model.Service;
import com.bookmypro.master_service.model.ServiceCategory;
import com.bookmypro.master_service.repository.ServiceCategoryProviderReposiory;
import com.bookmypro.master_service.repository.ServiceRepository;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceDetailsService {

    private final ServiceRepository serviceRepository;
    private final ServiceCategoryProviderReposiory serviceCategoryRepository;

    public List<ServiceDetailsDto> getAllServicesDetails() {
  
        List<Service> services = serviceRepository.findAllByStatusTrue();
        
        List<UUID> categoryIds = services.stream()
                .map(Service::getServiceCategoryId)
                .distinct()
                .toList();

        Map<UUID, ServiceCategory> categoryMap = Map.of();
        if (!categoryIds.isEmpty()) {
            categoryMap = serviceCategoryRepository.findAllById(categoryIds).stream()
                    .collect(Collectors.toMap(ServiceCategory::getId, Function.identity()));
        }

        final Map<UUID, ServiceCategory> finalCategoryMap = categoryMap;

        return services.stream()
                .map(service -> {
                    ServiceCategory category = finalCategoryMap.get(service.getServiceCategoryId());
                    return ServiceDetailsDto.builder()
                            .serviceId(service.getId())
                            .serviceName(service.getServiceName())
                            .estimatedDuration(service.getEstimatedDuration())
                            .serviceCategoryName(category != null ? category.getCategoryName() : null)
                            .serviceThumbnailUrl(category != null ? category.getIconUrl() : null)
                            .build();
                })
                .toList();
    }
}
