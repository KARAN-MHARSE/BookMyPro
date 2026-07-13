package com.bookmypro.customer_service.feature.address;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmypro.customer_service.common.request.Address;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerAddressController {
	private final CustomerAddressService service;

	@PostMapping("/address")
	public ResponseEntity<?> saveOrUpdateCustomeraAddress(@RequestBody CustomerAddressRequest request) {
		service.saveOrUpdateCustomerAddress(request);
		return ResponseEntity.ok("Addresss save or updated successfully");

	}

	@GetMapping("/address")
	public ResponseEntity<List<Address>> getCustomerAddresses(@RequestParam UUID credentialId) {

		List<Address> addresses = service.getCustomerAddresses(credentialId);

		return ResponseEntity.ok(addresses);
	}

	@GetMapping("/address/{addressId}")
	public ResponseEntity<Address> getAddressById(@PathVariable UUID addressId) {

		return ResponseEntity.ok(service.getAddressById(addressId));
	}
}
