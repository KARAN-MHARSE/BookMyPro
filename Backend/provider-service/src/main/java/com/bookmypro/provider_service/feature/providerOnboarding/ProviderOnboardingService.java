package com.bookmypro.provider_service.feature.providerOnboarding;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookmypro.provider_service.common.request.CreateCredentialRequest;
import com.bookmypro.provider_service.common.response.CreateCredentialResponse;
import com.bookmypro.provider_service.common.service.downstream.IdentityDownStreamService;
import com.bookmypro.provider_service.enums.ProviderStatus;
import com.bookmypro.provider_service.exception.BusinessException;
import com.bookmypro.provider_service.exception.ErrorCode;
import com.bookmypro.provider_service.model.Provider;
import com.bookmypro.provider_service.repository.ProviderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProviderOnboardingService {
	private final IdentityDownStreamService identityDownStreamService;
	private final ProviderRepository providerRepository;

	@Autowired
	@Lazy
	private ProviderOnboardingService self;

	@Transactional
	public ProviderOnboardingResponse onboardProvider(ProviderOnboardingRequest request) {
		log.info("Starting customer onboarding for email={}", request.getEmail());

		UUID credentialId = createCredential(request);
		try {

	        UUID providerId = self.saveProvider(credentialId);

	        return ProviderOnboardingResponse.builder()
	                .providerId(providerId)
	                .credentialId(credentialId)
	                .message("Customer successfully created")
	                .build();

	    } catch (Exception ex) {
	        identityDownStreamService.deleteCredential(credentialId);
	        throw ex;
	    }
	}

	private UUID createCredential(ProviderOnboardingRequest request) {
		CreateCredentialRequest credentialRequest = new CreateCredentialRequest(request.getEmail(),
				request.getUserName(), "PROVIDER",request.getPassword());

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
	protected UUID saveProvider(UUID credentialId) {
		Optional<Provider> customerOptional = providerRepository.findByCredentialId(credentialId);
		
		if(customerOptional.isPresent()) {
			return customerOptional.get().getProviderId();
		}
		Provider customer = Provider.builder().credentialId(credentialId).status(ProviderStatus.PENDING_VERIFICATION)
				.build();

		Provider savedCustomer = providerRepository.save(customer);

		return savedCustomer.getProviderId();
	}

}
