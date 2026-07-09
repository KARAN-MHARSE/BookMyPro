package com.bookmypro.identity_service.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmypro.identity_service.model.LoginHistory;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, UUID> {

}
