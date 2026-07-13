package com.bookmypro.master_service.provider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.getLookUps.LookupCriteria;
import com.bookmypro.master_service.feature.getLookUps.LookupDto;
import com.bookmypro.master_service.repository.ServiceCategoryProviderReposiory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceCategoryLookupProvider implements LookupProvider{
	private final ServiceCategoryProviderReposiory repository;

	@Override
	public LookupType getType() {
		return LookupType.SERVICE_CATEGORY;
	}

	@Override
	public List<LookupDto> getLookups(LookupCriteria criteria) {
		return repository.findByStatusTrue().stream()
				.map(sc -> new LookupDto(sc.getId(), sc.getCategoryCode(), sc.getCategoryName()))
				.toList();
	}
}
