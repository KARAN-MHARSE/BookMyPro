package com.bookmypro.master_service.feature.genericdetails;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmypro.master_service.enums.LookupType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/masters")
@RequiredArgsConstructor
public class MasterDetailsController {

    private final MasterDetailsService masterDetailsService;

    @PostMapping("/details")
    public ResponseEntity<Map<LookupType, List<MasterDetailsDto>>> getMasterDetails(
            @RequestBody MasterDetailsRequest request) {
        return ResponseEntity.ok(masterDetailsService.getMasterDetails(request));
    }
}
