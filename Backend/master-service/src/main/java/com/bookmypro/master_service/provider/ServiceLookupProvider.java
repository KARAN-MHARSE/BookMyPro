package com.bookmypro.master_service.provider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.getLookUps.LookupCriteria;
import com.bookmypro.master_service.feature.getLookUps.LookupDto;
import com.bookmypro.master_service.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceLookupProvider implements LookupProvider {

    private final ServiceRepository repository;

    @Override
    public LookupType getType() {
        return LookupType.SERVICE;
    }

    @Override
    public List<LookupDto> getLookups(LookupCriteria criteria) {

        if (criteria.getParentId() != null) {

            return repository.findByServiceCategoryIdAndStatusTrue(criteria.getParentId())
                    .stream()
                    .map(service -> new LookupDto(
                            service.getId(),
                            service.getServiceCode(),
                            service.getServiceName()))
                    .toList();
        }

        return repository.findByStatusTrue()
                .stream()
                .map(service -> new LookupDto(
                        service.getId(),
                        service.getServiceCode(),
                        service.getServiceName()))
                .toList();
    }
}