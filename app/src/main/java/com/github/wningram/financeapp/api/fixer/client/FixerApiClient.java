package com.github.wningram.financeapp.api.fixer.client;

import com.github.wningram.financeapp.api.fixer.dto.LatestRatesResponse;
import com.github.wningram.financeapp.api.fixer.dto.Rates;

import lombok.AllArgsConstructor;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Mono;

import java.time.Duration;

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

    public LatestRatesResponse getLatestRates() {
        try {
            // Build request: /latest?access_key={apiKey}
            Mono<LatestRatesResponse> mono = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/latest").queryParam("access_key", apiKey).build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, ClientResponse::createException)
                    .bodyToMono(LatestRatesResponse.class)
                    .timeout(Duration.ofSeconds(10));

            // Block for now to return a synchronous value; callers can be refactored to reactive if desired
            LatestRatesResponse response = mono.block();
            if (response == null) {
                // Fallback minimal response
                Rates rates = new Rates("USD", 1.0);
                return new LatestRatesResponse("USD", "1970-01-01", rates);
            }
            return response;
        } catch (Exception e) {
            // Log/propagate as needed - for now return a simple fallback object
            Rates rates = new Rates("USD", 1.0);
            return new LatestRatesResponse("USD", "1970-01-01", rates);
        }
    }

}