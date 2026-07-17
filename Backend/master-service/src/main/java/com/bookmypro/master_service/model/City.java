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
@Table(name = "mst_city")
public class City extends BaseEntity {

    @Column(name = "state_id", nullable = false)
    private UUID stateId;

    @Column(name = "city_code", nullable = false, unique = true, length = 20)
    private String cityCode;

    @Column(name = "city_name", nullable = false, length = 100)
    private String cityName;

    @Column(name = "pincode")
    private String pincode;
}
