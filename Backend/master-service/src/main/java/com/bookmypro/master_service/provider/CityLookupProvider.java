package com.bookmypro.master_service.provider;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.getLookUps.LookupCriteria;
import com.bookmypro.master_service.feature.getLookUps.LookupDto;
import com.bookmypro.master_service.repository.CityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityLookupProvider implements LookupProvider{
	private final CityRepository cityRepository;

	@Override
	public LookupType getType() {
		return LookupType.CITY;
	}

	@Override
	public List<LookupDto> getLookups(LookupCriteria criteria) {
		
		if(criteria.getParentId() != null) {
			return cityRepository.findByStateIdAndStatusTrue(criteria.getParentId())
					.stream()
					.map(city-> new LookupDto(city.getId(),city.getCityCode(),city.getCityName()))
					.collect(Collectors.toList());
		}
	 return cityRepository.findByStatusTrue()
				.stream()
				.map(city-> new LookupDto(city.getId(),city.getCityCode(),city.getCityName()))
				.collect(Collectors.toList());
	}

}
