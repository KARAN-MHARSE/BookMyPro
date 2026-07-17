package com.bookmypro.master_service.provider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.getlookups.LookupCriteria;
import com.bookmypro.master_service.feature.getlookups.LookupDto;
import com.bookmypro.master_service.repository.LanguageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageProvider implements LookupProvider {
	private final LanguageRepository repository;

	@Override
	public LookupType getType() {
		return LookupType.LANGUAGE;
	}

	@Override
	public List<LookupDto> getLookups(LookupCriteria criteria) {
		return repository.findByStatusTrue().stream()
				.map(lang -> new LookupDto(lang.getId(), lang.getCode(), lang.getName(),null))
				.toList();
	}

	@Override
	public List<com.bookmypro.master_service.feature.genericdetails.MasterDetailsDto> getDetailsByIds(List<java.util.UUID> ids) {
		return repository.findAllById(ids).stream()
				.map(lang -> new com.bookmypro.master_service.feature.genericdetails.MasterDetailsDto(lang.getId(), lang.getName()))
				.toList();
	}
}
