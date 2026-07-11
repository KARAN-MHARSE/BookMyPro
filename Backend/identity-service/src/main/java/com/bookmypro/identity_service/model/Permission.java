package com.bookmypro.identity_service.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID permissionId;

    private String permissionCode;

    private String resource;

    private String action;

    private String description;

    private String status;
}
