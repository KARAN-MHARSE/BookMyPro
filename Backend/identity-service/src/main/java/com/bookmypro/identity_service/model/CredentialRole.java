package com.bookmypro.identity_service.model;

import java.util.UUID;

import org.yaml.snakeyaml.events.Event.ID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credential_role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialRole {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
//	@Column(nullable = false)
	private UUID credentialId;
	
//	@Column(nullable = false)
	private UUID roleId;

}
