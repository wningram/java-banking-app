package com.github.wningram.financeapp.api.fixer;

import com.github.wningram.financeapp.api.fixer.client.FixerApiClient;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;


@Configuration
public class FixerModule {
	
	// Inject the fixer API key from application.properties or environment
	@Value("${fixer.api.key}")
	private String fixerApiKey;
	
	@Bean
	public FixerApiClient fixerApiClient() {
		return new FixerApiClient(
				fixerApiKey
			);
	}
}