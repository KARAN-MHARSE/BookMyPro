package com.bookmypro.customer_service.feature.address;

import java.util.UUID;

import com.bookmypro.customer_service.common.request.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressRequest {
	private UUID credentialId;
	private Address address;

}
