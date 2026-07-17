package com.bookmypro.master_service.feature.genericdetails;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.provider.LookupProvider;

@Service
public class MasterDetailsService {

    private final Map<LookupType, LookupProvider> providers;

    public MasterDetailsService(List<LookupProvider> providerList) {
        this.providers = providerList.stream()
                .collect(Collectors.toMap(LookupProvider::getType, Function.identity()));
    }

    public Map<LookupType, List<MasterDetailsDto>> getMasterDetails(MasterDetailsRequest request) {
        Map<LookupType, List<MasterDetailsDto>> response = new HashMap<>();

        if (request == null || request.getMasterIds() == null) {
            return response;
        }

        for (Map.Entry<LookupType, List<UUID>> entry : request.getMasterIds().entrySet()) {
            LookupType type = entry.getKey();
            List<UUID> ids = entry.getValue();

            if (ids == null || ids.isEmpty()) {
                continue;
            }

            LookupProvider provider = providers.get(type);
            if (provider != null) {
                response.put(type, provider.getDetailsByIds(ids));
            }
        }

        return response;
    }
}
