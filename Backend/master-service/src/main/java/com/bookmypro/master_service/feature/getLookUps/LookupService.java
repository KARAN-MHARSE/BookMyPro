package com.bookmypro.master_service.feature.getLookUps;

import java.awt.image.LookupTable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import java.util.function.Function;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.provider.LookupProvider;

@Service
public class LookupService {
	private final Map<LookupType, LookupProvider> providers;

	public LookupService(List<LookupProvider> providerList) {

		this.providers = providerList.stream().collect(Collectors.toMap(LookupProvider::getType, Function.identity()));
	}

	public Map<String, List<LookupDto>> getLookups(LookupRequest request) {

		Map<String, List<LookupDto>> response = new LinkedHashMap<>();

		for (LookupCriteria criteria : request.getLookups()) {

			LookupProvider provider = providers.get(criteria.getType());

			if (provider == null) {
				throw new RuntimeException("Unsupported lookup");
			}

			response.put(criteria.getType().name(), provider.getLookups(criteria));
		}

		return response;
	}

}