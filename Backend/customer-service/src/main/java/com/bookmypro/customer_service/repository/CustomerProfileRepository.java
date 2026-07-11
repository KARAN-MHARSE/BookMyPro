package com.bookmypro.customer_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.customer_service.model.CustomerProfile;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, UUID> {

}
