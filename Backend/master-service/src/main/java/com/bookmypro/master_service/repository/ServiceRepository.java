package com.bookmypro.master_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.master_service.model.Service;
import com.bookmypro.master_service.model.State;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
	
	public List<Service> findByStatusTrue();

	
	public List<Service> findByServiceCategoryIdAndStatusTrue(UUID serviecCategoryId);

	public List<Service> findAllByStatusTrue();

}
