package com.bookmypro.identity_service.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
	 @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http
	            .csrf(csrf -> csrf.disable())
	            .cors(Customizer.withDefaults())
	            .authorizeHttpRequests(auth -> auth
	                .anyRequest().permitAll()
	            );

	        return http.build();
	    }

	    @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();

	        configuration.setAllowedOriginPatterns(List.of("*"));
	        configuration.setAllowedMethods(List.of("*"));
	        configuration.setAllowedHeaders(List.of("*"));
	        configuration.setAllowCredentials(false);

	        UrlBasedCorsConfigurationSource source =
	                new UrlBasedCorsConfigurationSource();

	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
}
