package com.bookmypro.master_service.provider;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.bookmypro.master_service.enums.LookupType;
import com.bookmypro.master_service.feature.genericdetails.MasterDetailsDto;
import com.bookmypro.master_service.feature.getlookups.LookupCriteria;
import com.bookmypro.master_service.feature.getlookups.LookupDto;
import com.bookmypro.master_service.repository.SkillRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillLookupProvider implements LookupProvider {

    private final SkillRepository skillRepository;

    @Override
    public LookupType getType() {
        return LookupType.SKILL;
    }

    @Override
    public List<LookupDto> getLookups(LookupCriteria criteria) {
        return skillRepository.findByStatusTrue().stream()
                .map(skill -> new LookupDto(skill.getId(), skill.getSkillCode(), skill.getSkillName(), null))
                .toList();
    }

    @Override
    public List<MasterDetailsDto> getDetailsByIds(List<UUID> ids) {
        return skillRepository.findAllById(ids).stream()
                .map(skill -> new MasterDetailsDto(skill.getId(), skill.getSkillName()))
                .toList();
    }
}
