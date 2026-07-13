package com.bookmypro.customer_service.feature.profile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.customer_service.common.dto.LookupCriteria;
import com.bookmypro.customer_service.common.dto.LookupDto;
import com.bookmypro.customer_service.common.request.LookupRequest;
import com.bookmypro.customer_service.common.service.FileUploadService;
import com.bookmypro.customer_service.common.service.downstream.MasterDownStreamService;
import com.bookmypro.customer_service.enums.Gender;
import com.bookmypro.customer_service.enums.LookupType;
import com.bookmypro.customer_service.exception.BusinessException;
import com.bookmypro.customer_service.exception.ErrorCode;
import com.bookmypro.customer_service.model.Customer;
import com.bookmypro.customer_service.model.CustomerAddress;
import com.bookmypro.customer_service.model.CustomerProfile;
import com.bookmypro.customer_service.repository.CustomerAddressRepository;
import com.bookmypro.customer_service.repository.CustomerProfileRepository;
import com.bookmypro.customer_service.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerProfileService {
	private final CustomerRepository customerRepository;
	private final CustomerProfileRepository customerProfileRepository;
	private final CustomerAddressRepository customerAddressRepository;
	private final FileUploadService fileUploadService;
	private final MasterDownStreamService masterDownStreamService;
	private final ModelMapper mapper;

	@Transactional
	public void createOrUpdateCustomerProfile(CustomerProfileRequest request) {
		Customer customer = customerRepository.findByCredentialId(request.getCredentialId())
				.orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND));

		CustomerProfile profile = customerProfileRepository.findByCustomerId(customer.getCustomerId())
				.orElse(new CustomerProfile());

		profile.setCustomerId(customer.getCustomerId());
		profile.setFirstName(request.getFirstName());
		profile.setMiddleName(request.getMiddleName());
		profile.setLastName(request.getLastName());

		if (request.getDob() != null && !request.getDob().isEmpty()) {
			profile.setDob(LocalDate.parse(request.getDob()));
		}

		if (request.getGender() != null && !request.getGender().isEmpty()) {
			profile.setGender(Gender.valueOf(request.getGender()));
		}

		profile.setPreferredLanguage(request.getLanguage());
		profile.setOccupation(request.getOccupation());
//		profile.setAvatarUrl(request.getAvatarUrl());
		profile.setBio(request.getBio());

		if (request.getProfilePhoto() != null && !request.getProfilePhoto().isEmpty()) {
			List<String> profilePhotoUrl = fileUploadService.uploadFile(List.of(request.getProfilePhoto()));

			if (profilePhotoUrl != null && !profilePhotoUrl.isEmpty()) {
				profile.setAvatarUrl(profilePhotoUrl.get(0));
			}
		}

		customerProfileRepository.save(profile);

		if (request.getDefaultAddressId() != null) {
			updateDefaultAddress(customer.getCustomerId(), request.getDefaultAddressId());
		}

		System.out.println(request);
	}

	private void updateDefaultAddress(UUID customerId, UUID defaultAddressId) {
		List<CustomerAddress> addresses = customerAddressRepository.findByCustomerId(customerId);

		addresses.forEach(address -> {
			address.setIsDefault(false);
		});

		customerAddressRepository.saveAll(addresses);

		CustomerAddress selectedAddress = customerAddressRepository.findById(defaultAddressId)
				.orElseThrow(() -> new BusinessException(ErrorCode.ADDRESS_NOT_FOUND));

		selectedAddress.setIsDefault(true);

		customerAddressRepository.save(selectedAddress);
	}

	public CustomerProfileResponse getCustomerProfile(UUID credentialId) {
		CustomerProfileResponse response = new CustomerProfileResponse();

		Customer customer = customerRepository.findByCredentialId(credentialId)
				.orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND));

		CustomerProfile profile = customerProfileRepository.findByCustomerId(customer.getCustomerId())
				.orElse(new CustomerProfile());

		PersonalInfo personalInfo = mapper.map(profile, PersonalInfo.class);

		List<CustomerAddress> defaultAddress = customerAddressRepository
				.findByCustomerIdAndIsDefault(customer.getCustomerId(), true);

		if (profile != null && profile.getAvatarUrl() != null && !profile.getAvatarUrl().isBlank()) {
			byte[] profileFile = fileUploadService.getFile(profile.getAvatarUrl());
			personalInfo.setProfilePhoto(profileFile);
		}

		Map<String, List<LookupDto>> lookups = getLookupsData();
		response.setLookups(lookups);

		response.setPersonalInfo(personalInfo);
		response.setDefaultAddress(defaultAddress != null && defaultAddress.size() > 0 ? defaultAddress.get(0) : null);

		return response;

	}

	private Map<String, List<LookupDto>> getLookupsData() {
		LookupRequest lookupRequest = new LookupRequest();
		LookupCriteria lookupCriteria = new LookupCriteria();
		lookupCriteria.setType(LookupType.LANGUAGE);
		lookupRequest.setLookups(List.of(lookupCriteria));
		ResponseEntity<Map<String, List<LookupDto>>> lookupsResponseBody = masterDownStreamService
				.getLookups(lookupRequest);
		if (lookupsResponseBody.getBody() != null) {
			return lookupsResponseBody.getBody();
		}

		return null;
	}

}
