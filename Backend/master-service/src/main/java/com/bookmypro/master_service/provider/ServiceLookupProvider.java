package com.bookmypro.master_service.provider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.getlookups.LookupCriteria;
import com.bookmypro.master_service.feature.getlookups.LookupDto;
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
                            service.getServiceName(),
                            service.getServiceCategoryId()))
                    .toList();
        }

        return repository.findByStatusTrue()
                .stream()
                .map(service -> new LookupDto(
                        service.getId(),
                        service.getServiceCode(),
                        service.getServiceName(),
                        service.getServiceCategoryId()))
                .toList();
    }

    @Override
    public List<com.bookmypro.master_service.feature.genericdetails.MasterDetailsDto> getDetailsByIds(List<java.util.UUID> ids) {
        return repository.findAllById(ids).stream()
                .map(service -> new com.bookmypro.master_service.feature.genericdetails.MasterDetailsDto(service.getId(), service.getServiceName()))
                .toList();
    }
}
