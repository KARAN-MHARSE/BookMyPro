package com.bookmypro.master_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mst_country")
public class Country extends BaseEntity {

    @Column(name = "country_code", nullable = false, unique = true, length = 10)
    private String countryCode;

    @Column(name = "country_name", nullable = false, length = 100)
    private String countryName;

    @Column(name = "iso_code", nullable = false, unique = true, length = 5)
    private String isoCode;
}
