package com.bookmypro.master_service.provider;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.getLookUps.LookupCriteria;
import com.bookmypro.master_service.feature.getLookUps.LookupDto;
import com.bookmypro.master_service.repository.StateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StateLookupProvider implements LookupProvider {
	private final StateRepository stateRepository;

	@Override
	public LookupType getType() {
		return LookupType.STATE;
	}

	@Override
	public List<LookupDto> getLookups(LookupCriteria criteria) {

		if (criteria.getParentId() != null) {
			return stateRepository.findByCountryIdAndStatusTrue(criteria.getParentId()).stream()
					.map(state -> new LookupDto(state.getId(), state.getStateCode(), state.getStateName()))
					.collect(Collectors.toList());
		}
		return stateRepository.findByStatusTrue().stream()
				.map(state -> new LookupDto(state.getId(), state.getStateCode(), state.getStateName()))
				.collect(Collectors.toList());
	}
}
