package com.bookmypro.customer_service.feature.onboarding;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientResponseException;

import com.bookmypro.customer_service.common.request.CreateCredentialRequest;
import com.bookmypro.customer_service.common.response.CreateCredentialResponse;
import com.bookmypro.customer_service.common.service.downstream.IdentityDownStreamService;
import com.bookmypro.customer_service.enums.CustomerStatus;
import com.bookmypro.customer_service.exception.BusinessException;
import com.bookmypro.customer_service.exception.DownstreamServiceException;
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

		} catch (RestClientResponseException ex) {
			log.error("Identity service returned error status={} body={}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
			ProblemDetail detail = null;
			try {
				detail = ex.getResponseBodyAs(ProblemDetail.class);
			} catch (Exception parseEx) {
				log.warn("Failed to parse identity error as ProblemDetail", parseEx);
			}
			String message = (detail != null && detail.getDetail() != null) ? detail.getDetail() : "Identity service error";
			String code = (detail != null && detail.getProperties() != null && detail.getProperties().get("code") != null)
					? detail.getProperties().get("code").toString()
					: null;
			throw new DownstreamServiceException(
					HttpStatus.valueOf(ex.getStatusCode().value()),
					message,
					code
			);
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
