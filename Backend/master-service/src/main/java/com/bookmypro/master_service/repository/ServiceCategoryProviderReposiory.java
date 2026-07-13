package com.bookmypro.master_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.master_service.model.ServiceCategory;

public interface ServiceCategoryProviderReposiory extends JpaRepository<ServiceCategory, UUID> {
	public List<ServiceCategory> findByStatusTrue();

}
