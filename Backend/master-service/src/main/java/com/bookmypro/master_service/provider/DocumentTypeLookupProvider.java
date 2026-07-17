package com.bookmypro.master_service.provider;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.genericdetails.MasterDetailsDto;
import com.bookmypro.master_service.feature.getlookups.LookupCriteria;
import com.bookmypro.master_service.feature.getlookups.LookupDto;
import com.bookmypro.master_service.repository.DocumentTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentTypeLookupProvider implements LookupProvider {

    private final DocumentTypeRepository documentTypeRepository;

    @Override
    public LookupType getType() {
        return LookupType.DOCUMENT_TYPE;
    }

    @Override
    public List<LookupDto> getLookups(LookupCriteria criteria) {
        return documentTypeRepository.findByStatusTrue().stream()
                .map(doc -> new LookupDto(doc.getId(), doc.getCode(), doc.getName(), null))
                .toList();
    }

    @Override
    public List<MasterDetailsDto> getDetailsByIds(List<UUID> ids) {
        return documentTypeRepository.findAllById(ids).stream()
                .map(doc -> new MasterDetailsDto(doc.getId(), doc.getName()))
                .toList();
    }
}
