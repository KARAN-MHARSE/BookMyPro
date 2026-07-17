package com.bookmypro.master_service.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mst_service_category")
public class ServiceCategory extends BaseEntity {

    @Column(name = "category_code", nullable = false, unique = true, length = 30)
    private String categoryCode;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "icon_url")
    private String iconUrl;
}
