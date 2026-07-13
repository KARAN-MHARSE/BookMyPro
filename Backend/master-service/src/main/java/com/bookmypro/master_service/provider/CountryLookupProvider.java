package com.bookmypro.master_service.provider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.getLookUps.LookupCriteria;
import com.bookmypro.master_service.feature.getLookUps.LookupDto;
import com.bookmypro.master_service.repository.CountryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryLookupProvider implements LookupProvider {
	private final CountryRepository repository;

	@Override
	public LookupType getType() {
		return LookupType.COUNTRY;
	}

	@Override
	public List<LookupDto> getLookups(LookupCriteria criteria) {
		return repository.findByStatusTrue().stream()
				.map(country -> new LookupDto(country.getId(), country.getCountryCode(), country.getCountryName()))
				.toList();
	}

}
