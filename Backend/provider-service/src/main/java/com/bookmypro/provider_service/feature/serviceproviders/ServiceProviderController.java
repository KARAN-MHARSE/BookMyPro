package com.bookmypro.provider_service.feature.serviceproviders;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmypro.provider_service.common.dto.ProviderDto;
import com.bookmypro.provider_service.common.dto.ReviewDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceProviderController {

    private final ServiceProviderService service;

    @GetMapping("/providers")
    public ResponseEntity<List<ProviderDto>> getServiceProviders(
            @RequestParam UUID serviceID,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        return ResponseEntity.ok(service.getServiceProviders(serviceID, pageNo, pageSize));
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDto>> getReviews(
            @RequestParam(required = false) UUID serviceID,
            @RequestParam(required = false) UUID providerID,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        return ResponseEntity.ok(service.getReviews(serviceID, providerID, pageNo, pageSize));
    }
}
