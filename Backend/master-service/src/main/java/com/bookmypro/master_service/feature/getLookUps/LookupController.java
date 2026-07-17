package com.bookmypro.master_service.feature.getlookups;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lookups")
@RequiredArgsConstructor
public class LookupController {

    private final LookupService lookupService;

    @PostMapping
    public ResponseEntity<Map<String, List<LookupDto>>> getLookups(
            @RequestBody LookupRequest request) {

        return ResponseEntity.ok(
                lookupService.getLookups(request));
    }

}
