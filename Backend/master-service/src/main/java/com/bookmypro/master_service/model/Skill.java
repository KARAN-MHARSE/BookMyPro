package com.bookmypro.master_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mst_skill")
public class Skill extends BaseEntity {

	@Column(name = "skill_code", nullable = false, unique = true, length = 30)
	private String skillCode;

	@Column(name = "skill_name", nullable = false, length = 100)
	private String skillName;

	@Column(name = "description", length = 500)
	private String description;
}