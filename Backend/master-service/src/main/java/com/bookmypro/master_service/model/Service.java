package com.bookmypro.master_service.model;


import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mst_service")
public class Service extends BaseEntity {

    @Column(name = "service_category_id", nullable = false)
    private UUID serviceCategoryId;

    @Column(name = "service_code", nullable = false, unique = true, length = 30)
    private String serviceCode;

    @Column(name = "service_name", nullable = false, length = 150)
    private String serviceName;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "estimated_duration")
    private Integer estimatedDuration;

    @Column(name = "base_price", precision = 12, scale = 2)
    private BigDecimal basePrice;
}
