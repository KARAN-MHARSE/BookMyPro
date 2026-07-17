package com.bookmypro.MarketplaceService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.bookmypro.MarketplaceService.common.services.downstream.IdentityDownStreamService;
import com.bookmypro.MarketplaceService.common.services.downstream.MasterDownStreamService;
import com.bookmypro.MarketplaceService.common.services.downstream.ProviderDownStreamService;


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
	
	@Bean 
	MasterDownStreamService masterDownStreamService(
			RestClient.Builder builder,
			@Value("${master_base_url}") String baseUrl
			) {
		RestClient restClient = builder
				.baseUrl(baseUrl)
				.build();
		
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builderFor(RestClientAdapter.create(restClient))
				.build();
		
		return factory.createClient(MasterDownStreamService.class);
		
	}
	
	@Bean 
	ProviderDownStreamService providerDownStreamService(
			RestClient.Builder builder,
			@Value("${provider_base_url}") String baseUrl
			) {
		RestClient restClient = builder
				.baseUrl(baseUrl)
				.build();
		
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builderFor(RestClientAdapter.create(restClient))
				.build();
		
		return factory.createClient(ProviderDownStreamService.class);
		
	}
	
	

}
