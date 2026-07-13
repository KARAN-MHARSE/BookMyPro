package com.bookmypro.master_service.model;


import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mst_state")
public class State extends BaseEntity {

    @Column(name = "country_id", nullable = false)
    private UUID countryId;

    @Column(name = "state_code", nullable = false, unique = true, length = 20)
    private String stateCode;

    @Column(name = "state_name", nullable = false, length = 100)
    private String stateName;
}