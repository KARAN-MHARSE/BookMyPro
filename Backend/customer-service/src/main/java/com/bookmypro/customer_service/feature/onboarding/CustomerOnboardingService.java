package com.bookmypro.customer_service.feature.onboarding;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.customer_service.common.request.CreateCredentialRequest;
import com.bookmypro.customer_service.common.response.CreateCredentialResponse;
import com.bookmypro.customer_service.common.service.downstream.IdentityDownStreamService;
import com.bookmypro.customer_service.enums.CustomerStatus;
import com.bookmypro.customer_service.exception.BusinessException;
import com.bookmypro.customer_service.exception.ErrorCode;
import com.bookmypro.customer_service.model.Customer;
import com.bookmypro.customer_service.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerOnboardingService {
	private final IdentityDownStreamService identityDownStreamService;
	private final CustomerRepository customerRepository;

	@Autowired
	@Lazy
	private CustomerOnboardingService self;

	public CustomerOnboardingResponse onboardCustomer(CustomerOnboardingRequest request) {
		log.info("Starting customer onboarding for email={}", request.getEmail());

		UUID credentialId = createCredential(request);
		try {

	        UUID customerId = self.saveCustomer(credentialId);

	        return CustomerOnboardingResponse.builder()
	                .customerId(customerId)
	                .credentialId(credentialId)
	                .message("Customer successfully created")
	                .build();

	    } catch (Exception ex) {
	        identityDownStreamService.deleteCredential(credentialId);
	        throw ex;
	    }
	}

	private UUID createCredential(CustomerOnboardingRequest request) {
		CreateCredentialRequest credentialRequest = new CreateCredentialRequest(request.getEmail(),
				request.getUserName(), "CUSTOMER",request.getPassword());

		try {
			var response = identityDownStreamService.createCredentials(credentialRequest);

			if (response == null || response.getBody() == null) {
				throw new BusinessException(ErrorCode.SOMETHING_WENT_WRONG);
			}
			CreateCredentialResponse createCredentialResponse = response.getBody();

			if (createCredentialResponse.getCredentialId() == null) {
				throw new BusinessException(ErrorCode.INVALID_IDENTITY_RESPONSE);
			}
			return UUID.fromString(createCredentialResponse.getCredentialId());

		} catch (BusinessException ex) {
			log.error("Identity service rejected credential creation: {}", ex.getMessage(), ex);
			throw ex;
		} catch (IllegalArgumentException ex) {
			log.error("Invalid credentialId returned from Identity Service");
			throw new BusinessException(ErrorCode.INVALID_IDENTITY_RESPONSE);
		} catch (Exception ex) {
			log.error("Customer onboarding failed", ex);
			throw new BusinessException(ErrorCode.IDENTITY_SERVICE_UNAVAILABLE);
		}
	}

	@Transactional
	protected UUID saveCustomer(UUID credentialId) {
		Optional<Customer> customerOptional = customerRepository.findByCredentialId(credentialId);
		
		if(customerOptional.isPresent()) {
			return customerOptional.get().getCustomerId();
		}
		Customer customer = Customer.builder().credentialId(credentialId).status(CustomerStatus.PENDING_VERIFICATION)
				.build();

		Customer savedCustomer = customerRepository.save(customer);

		return savedCustomer.getCustomerId();
	}

}
