package com.github.wningram.financeapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.wningram.financeapp.api.fixer.LatestRatesResponse;
import com.github.wningram.financeapp.api.fixer.client.FixerApiClient;
import com.github.wningram.financeapp.api.fixer.dto.Rate;
import com.github.wningram.financeapp.data.RateEntity;
import com.github.wningram.financeapp.data.RateRepository;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/convert")
public class ConversionController {
	
	private FixerApiClient fixerApiClient;
	private RateRepository rateRepository;
	
	public ConversionController(FixerApiClient fixerApiClient, RateRepository rateRepository) {
		this.fixerApiClient = fixerApiClient;
		this.rateRepository = rateRepository;
	}
	
	@GetMapping
	public Mono<Rate> convertCurrency(@RequestParam String fromCurrency, @RequestParam String toCurrency) {
		String fromCurrencyUpper = fromCurrency.toUpperCase();
		String toCurrencyUpper = toCurrency.toUpperCase();
		
		// Check db cache for recent conversion rate
		RateEntity resultRate = rateRepository.findByCurrencyCode(fromCurrencyUpper);
		if (resultRate != null) {
			return Mono.just(new Rate(resultRate.getCurrencyCode(), resultRate.getRate()));
		}
		
		System.out.println(String.format("No cached conversion rate found for %s -> %s. Fetching from API...", fromCurrencyUpper, toCurrencyUpper));
		Mono<LatestRatesResponse> response = fixerApiClient.getLatestRates(fromCurrencyUpper);
		Mono<Rate> rateDto = response.map(ratesResponse -> {
			try {
				for (Rate rate : ratesResponse.getRates()) {
					System.out.println(String.format("Currency: %s, Rate: %s", rate.getCurrencyCode(), rate.getRate()));
					if (rate.getCurrencyCode().equals(toCurrencyUpper)) {
						// Cache the result in the db
						RateEntity rateEntity = new RateEntity(rate.getCurrencyCode(), rate.getRate());
						rateRepository.save(rateEntity);
						System.out.println(String.format("Fetched conversion rate from API: %s -> %s = %s", fromCurrencyUpper, toCurrencyUpper, rate.getRate()));
						System.out.println("Cached conversion rate in database.");

						return rate;
					}
				}
			} catch (Exception e) {
				System.err.println("Error processing API response: " + e.getMessage());
			}
			return null; // No matching currency found in API response
		});
		
		return rateDto;
	}

}
