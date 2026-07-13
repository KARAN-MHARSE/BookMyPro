package com.bookmypro.customer_service.feature.profile;

import java.util.List;
import java.util.Map;

import com.bookmypro.customer_service.common.dto.LookupDto;
import com.bookmypro.customer_service.model.CustomerAddress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfileResponse {
	private PersonalInfo personalInfo;
	private CustomerAddress defaultAddress;
	Map<String, List<LookupDto>> lookups;

}
