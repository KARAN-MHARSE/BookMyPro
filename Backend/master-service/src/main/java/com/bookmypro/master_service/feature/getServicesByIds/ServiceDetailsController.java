package com.bookmypro.master_service.feature.getservicesbyids;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceDetailsController {

    private final ServiceDetailsService serviceDetailsService;

    @GetMapping("/summary")
    public ResponseEntity<List<ServiceDetailsDto>> getAllServicesDetails() {
        
        return ResponseEntity.ok(serviceDetailsService.getAllServicesDetails());
    }

}
