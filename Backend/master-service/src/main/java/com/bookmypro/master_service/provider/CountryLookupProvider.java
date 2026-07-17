package com.bookmypro.master_service.provider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.getlookups.LookupCriteria;
import com.bookmypro.master_service.feature.getlookups.LookupDto;
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
				.map(country -> new LookupDto(country.getId(), country.getCountryCode(), country.getCountryName(),null))
				.toList();
	}

	@Override
	public List<com.bookmypro.master_service.feature.genericdetails.MasterDetailsDto> getDetailsByIds(List<java.util.UUID> ids) {
		return repository.findAllById(ids).stream()
				.map(country -> new com.bookmypro.master_service.feature.genericdetails.MasterDetailsDto(country.getId(), country.getCountryName()))
				.toList();
	}

}
