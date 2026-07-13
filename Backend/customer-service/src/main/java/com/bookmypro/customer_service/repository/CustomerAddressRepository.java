package com.bookmypro.customer_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.customer_service.common.request.Address;
import com.bookmypro.customer_service.model.Customer;
import com.bookmypro.customer_service.model.CustomerAddress;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, UUID> {

	List<CustomerAddress> findByCustomerId(UUID customerId);
	
	List<CustomerAddress> findByCustomerIdAndIsDefault(UUID customerId, Boolean isDefault);

	List<CustomerAddress> findByCustomerIdAndIsActiveOrderByIsDefaultDescCreatedAtDesc(UUID customerId, boolean b);

	Optional<CustomerAddress> findByAddressIdAndIsActive(UUID addressId, boolean isActive);
}
