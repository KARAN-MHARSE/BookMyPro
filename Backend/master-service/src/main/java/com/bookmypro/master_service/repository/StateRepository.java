package com.bookmypro.master_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.master_service.model.City;
import com.bookmypro.master_service.model.State;

public interface StateRepository extends JpaRepository<State, UUID> {
	public List<State> findByStatusTrue();

	public List<State> findByCountryIdAndStatusTrue(UUID stateId);

}
