package com.bookmypro.customer_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.bookmypro.customer_service.common.service.downstream.IdentityDownStreamService;

@Configuration
public class HttpClientConfig {
	
	@Bean
	IdentityDownStreamService identityDownStreamService(
			RestClient.Builder builder,
			@Value("${identity_base_url}") String baseUrl) {
		RestClient restClient = builder
				.baseUrl(baseUrl)
				.build();
		
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builderFor(RestClientAdapter.create(restClient))
				.build();
		
		return factory.createClient(IdentityDownStreamService.class);
	}

}
