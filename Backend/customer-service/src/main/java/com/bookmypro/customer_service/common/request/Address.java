package com.bookmypro.customer_service.common.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	private UUID addressId;
	private String addressType;
    private String addressName;
    private String landmark;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
    private String state;
    private String country;
    private String postalCode;
    private Boolean defaultAddress;
    private String latitude;
    private String longitude;
}
