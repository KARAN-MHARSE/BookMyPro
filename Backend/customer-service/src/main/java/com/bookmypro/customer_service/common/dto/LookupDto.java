package com.bookmypro.customer_service.common.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LookupDto {

	private UUID id;
	private String code;
	private String name;
	private UUID parentValue;

}