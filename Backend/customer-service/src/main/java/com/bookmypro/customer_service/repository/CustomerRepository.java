package com.bookmypro.customer_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.customer_service.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
	Optional<Customer> findByCredentialId(UUID credentialId);

}
