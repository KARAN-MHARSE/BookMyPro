package com.bookmypro.customer_service.feature.address;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.customer_service.common.request.Address;
import com.bookmypro.customer_service.enums.AddressType;
import com.bookmypro.customer_service.exception.BusinessException;
import com.bookmypro.customer_service.exception.ErrorCode;
import com.bookmypro.customer_service.model.Customer;
import com.bookmypro.customer_service.model.CustomerAddress;
import com.bookmypro.customer_service.repository.CustomerAddressRepository;
import com.bookmypro.customer_service.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerAddressService {

	private final CustomerRepository customerRepository;
	private final CustomerAddressRepository customerAddressRepository;

	@Transactional
	public void saveOrUpdateCustomerAddress(CustomerAddressRequest request) {

		Customer customer = customerRepository.findByCredentialId(request.getCredentialId())
				.orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND));

		Address addressRequest = request.getAddress();

		CustomerAddress customerAddress;

		if (addressRequest.getAddressId() != null) {

			customerAddress = customerAddressRepository.findById(addressRequest.getAddressId())
					.orElseThrow(() -> new BusinessException(ErrorCode.ADDRESS_NOT_FOUND));

			if (!customerAddress.getCustomerId().equals(customer.getCustomerId())) {
				throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND);
			}
		} else {
			customerAddress = new CustomerAddress();
			customerAddress.setCustomerId(customer.getCustomerId());
			customerAddress.setIsActive(true);
		}

		mapToCustomerAddress(addressRequest, customerAddress);

		if (Boolean.TRUE.equals(customerAddress.getIsDefault())) {
			List<CustomerAddress> defaultAddresses =
			        customerAddressRepository.findByCustomerIdAndIsDefault(
			                customer.getCustomerId(),
			                true
			        );

			defaultAddresses.forEach(address -> {
			    if (!address.getAddressId().equals(customerAddress.getAddressId())) {
			        address.setIsDefault(false);
			    }
			});

			customerAddressRepository.saveAll(defaultAddresses);
		}

		customerAddressRepository.save(customerAddress);
	}

	private void mapToCustomerAddress(Address request, CustomerAddress entity) {

		entity.setAddressType(request.getAddressType() != null ? AddressType.valueOf(request.getAddressType()) : null);

		entity.setAddressName(request.getAddressName());
		entity.setLine1(request.getAddressLine1());
		entity.setLine2(request.getAddressLine2());
		entity.setLandmark(request.getLandmark());
		entity.setCity(request.getCity());
		entity.setDistrict(request.getDistrict());
		entity.setState(request.getState());
		entity.setCountry(request.getCountry());
		entity.setPostalCode(request.getPostalCode());

		entity.setLatitude(request.getLatitude() != null && !request.getLatitude().isBlank()
				? new BigDecimal(request.getLatitude())
				: null);

		entity.setLongitude(request.getLongitude() != null && !request.getLongitude().isBlank()
				? new BigDecimal(request.getLongitude())
				: null);

		entity.setIsDefault(Boolean.TRUE.equals(request.getDefaultAddress()));
	}

	@Transactional(readOnly = true)
	public List<Address> getCustomerAddresses(UUID credentialId) {

		Customer customer = customerRepository.findByCredentialId(credentialId)
				.orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND));

		return customerAddressRepository
				.findByCustomerIdAndIsActiveOrderByIsDefaultDescCreatedAtDesc(customer.getCustomerId(), true).stream()
				.map(this::mapToResponse).toList();
	}

	private Address mapToResponse(CustomerAddress entity) {

		return new Address(entity.getAddressId(),
				entity.getAddressType() != null ? entity.getAddressType().name() : null, entity.getAddressName(),
				entity.getLandmark(), entity.getLine1(), entity.getLine2(), entity.getCity(), entity.getDistrict(),
				entity.getState(), entity.getCountry(), entity.getPostalCode(), entity.getIsDefault(),
				entity.getLatitude() != null ? entity.getLatitude().toPlainString() : null,
				entity.getLongitude() != null ? entity.getLongitude().toPlainString() : null);
	}

	@Transactional(readOnly = true)
	public Address getAddressById(UUID addressId) {

		CustomerAddress customerAddress = customerAddressRepository.findByAddressIdAndIsActive(addressId, true)
				.orElseThrow(() -> new BusinessException(ErrorCode.ADDRESS_NOT_FOUND));

		return mapToResponse(customerAddress);
	}

}
