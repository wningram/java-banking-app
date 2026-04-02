package com.github.wningram.financeapp.api.fixer.client;

import com.github.wningram.financeapp.api.fixer.LatestRatesResponse;
import com.github.wningram.financeapp.api.fixer.dto.Rate;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@AllArgsConstructor
public class FixerApiClient {

    private String apiKey;
    private String baseUrl = "http://data.fixer.io/api"; // default Fixer endpoint (replace if using paid plan)
    private WebClient webClient;

    public FixerApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    // For tests or custom WebClient
    public FixerApiClient(String apiKey, WebClient webClient) {
        this.apiKey = apiKey;
        this.webClient = webClient;
    }
    
    public Mono<LatestRatesResponse> getLatestRates() {
		return getLatestRates(null);
	}

    public Mono<LatestRatesResponse> getLatestRates(@Nullable String baseCurrency) {
		try {
			return webClient.get()
					.uri(uriBuilder -> uriBuilder.path("/latest")
							.queryParam("access_key", apiKey)
							.queryParam("base", baseCurrency)
							.build())
					.retrieve()
					.onStatus(HttpStatusCode::isError, ClientResponse::createException)
					.bodyToMono(LatestRatesResponse.class)
					.doOnError(WebClientResponseException.class,  e -> {
						Map<String, Object> errorBody = e.getResponseBodyAs(new ParameterizedTypeReference<Map<String, Object>>() {});
						System.err.println(String.format("Error response from Fixer API: %s", errorBody));
					})
					.onErrorReturn(new LatestRatesResponse(baseCurrency != null ? baseCurrency : "EUR", "2024-01-01", new Rate[] { new Rate("USD", 1.0) }))
					.timeout(Duration.ofSeconds(10));

		} catch (Exception e) {
			// Log/propagate as needed - for now return a simple fallback object
			System.err.println("Error fetching latest rates from Fixer API: " + e.getMessage());

			Rate[] rates = new Rate[] { new Rate("USD", 1.0) };
			return Mono.just(new LatestRatesResponse(baseCurrency != null ? baseCurrency : "EUR", "2024-01-01", rates));
		}
	}
}