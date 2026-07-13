package com.bookmypro.master_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.master_service.model.City;
import com.bookmypro.master_service.model.Country;

public interface CityRepository extends JpaRepository<City, UUID>{
	public List<City> findByStatusTrue();

	public List<City> findByStateIdAndStatusTrue(UUID stateId);
}
