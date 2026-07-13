	package com.bookmypro.master_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
	@Setter
	@Entity
	@Table(name = "mst_currency")
	public class Currency extends BaseEntity {

	    @Column(name = "code", nullable = false, unique = true, length = 10)
	    private String code;

	    @Column(name = "name", nullable = false, length = 50)
	    private String name;

	    @Column(name = "symbol", nullable = false, length = 10)
	    private String symbol;
	}