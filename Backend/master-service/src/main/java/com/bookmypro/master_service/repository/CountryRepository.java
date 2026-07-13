package com.bookmypro.master_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.master_service.model.Country;

public interface CountryRepository extends JpaRepository<Country, UUID>{

	public List<Country> findByStatusTrue();

}
