package com.bookmypro.identity_service.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.identity_service.model.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
	Optional<Role> findByRoleCode(String roleCode);

	List<Role> findByRoleIdIn(List<UUID> roleIds);

}
