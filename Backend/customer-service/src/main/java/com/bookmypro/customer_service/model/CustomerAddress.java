package com.bookmypro.customer_service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.bookmypro.customer_service.enums.AddressType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID addressId;

	private UUID customerId;

	@Enumerated(EnumType.STRING)
	private AddressType addressType;

	private String addressName;

	private String line1;
	private String line2;
	private String landmark;
	private String city;
	private String district;
	private String state;
	private String country;
	private String postalCode;

	private BigDecimal latitude;
	private BigDecimal longitude;

	private Boolean isDefault;
	private Boolean isActive;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
