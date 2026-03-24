package com.github.wningram.financeapp.api.fixer.client;

import com.github.wningram.financeapp.api.fixer.dto.LatestRatesResponse;
import com.github.wningram.financeapp.api.fixer.dto.Rates;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Test suite for FixerApiClient
 * Tests HTTP client behavior for Fixer API currency exchange rate requests
 */
public class FixerApiClientTest {

    private static final String TEST_API_KEY = "test-api-key-12345";
    private static final String TEST_BASE_URL = "http://data.fixer.io/api";
    private static final String TEST_BASE_CURRENCY = "USD";
    private static final String TEST_DATE = "2026-03-22";
    private static final double TEST_RATE = 1.15;

    @Mock
    private WebClient mockWebClient;

    private FixerApiClient fixerApiClient;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fixerApiClient = new FixerApiClient(TEST_API_KEY, mockWebClient);
    }


    /**
     * Test 1: Constructor with API key only
     * Verifies FixerApiClient can be created with just an API key
     */
    @Test
    public void testConstructorWithApiKeyOnly() {
        // Act
        FixerApiClient client = new FixerApiClient(TEST_API_KEY);

        // Assert
        assertNotNull(client, "Client should be created");
    }

    /**
     * Test 2: Constructor with API key and WebClient
     * Verifies FixerApiClient can be created with API key and WebClient
     */
    @Test
    public void testConstructorWithApiKeyAndWebClient() {
        // Act
        FixerApiClient client = new FixerApiClient(TEST_API_KEY, mockWebClient);

        // Assert
        assertNotNull(client, "Client should be created");
    }

    /**
     * Test 3: Constructor with all parameters via AllArgsConstructor
     * Verifies FixerApiClient can be created with all arguments
     */
    @Test
    public void testAllArgsConstructor() {
        // Act
        FixerApiClient client = new FixerApiClient(TEST_API_KEY, TEST_BASE_URL, mockWebClient);

        // Assert
        assertNotNull(client, "Client should be created with all parameters");
    }

    /**
     * Test 4: Fallback response when exception occurs
     * Verifies that getLatestRates handles exceptions gracefully
     */
    @Test
    public void testGetLatestRatesFallbackOnException() {
        // Arrange
        when(mockWebClient.get()).thenThrow(new RuntimeException("Network error"));

        // Act
        LatestRatesResponse actualResponse = fixerApiClient.getLatestRates();

        // Assert
        assertNotNull(actualResponse, "Response should not be null (fallback)");
        assertEquals(actualResponse.getBase(), "USD");
        assertEquals(actualResponse.getDate(), "1970-01-01");
        assertNotNull(actualResponse.getRates());
        assertEquals(actualResponse.getRates().getCurrencyCode(), "USD");
        assertEquals(actualResponse.getRates().getRate(), 1.0);
    }

    /**
     * Test 5: Fallback response when null mono.block() result
     * Verifies fallback behavior when response is empty
     */
    @Test
    public void testGetLatestRatesFallbackOnNullResponse() {
        // Arrange - Create a real WebClient to test timeout/null handling
        WebClient realWebClient = WebClient.builder()
                .baseUrl("http://nonexistent-api-that-times-out.local")
                .build();
        FixerApiClient testClient = new FixerApiClient(TEST_API_KEY, realWebClient);

        // Act - This will timeout and return null
        LatestRatesResponse actualResponse = testClient.getLatestRates();

        // Assert
        assertNotNull(actualResponse, "Response should not be null (fallback)");
        assertEquals(actualResponse.getBase(), "USD");
        assertEquals(actualResponse.getDate(), "1970-01-01");
        assertEquals(actualResponse.getRates().getRate(), 1.0);
    }

    /**
     * Test 6: Response object contains correct data
     * Verifies the LatestRatesResponse can hold and return rate data
     */
    @Test
    public void testLatestRatesResponseDataIntegrity() {
        // Arrange
        Rates testRates = new Rates("EUR", TEST_RATE);
        LatestRatesResponse response = new LatestRatesResponse(
                TEST_BASE_CURRENCY,
                TEST_DATE,
                testRates
        );

        // Act & Assert
        assertEquals(response.getBase(), TEST_BASE_CURRENCY);
        assertEquals(response.getDate(), TEST_DATE);
        assertEquals(response.getRates().getCurrencyCode(), "EUR");
        assertEquals(response.getRates().getRate(), TEST_RATE);
    }

    /**
     * Test 7: Multiple Rates objects with different currencies
     * Verifies Rates class handles different currency codes
     */
    @Test
    public void testRatesWithMultipleCurrencies() {
        // Act
        Rates eurRates = new Rates("EUR", 1.10);
        Rates gbpRates = new Rates("GBP", 0.85);
        Rates jpyRates = new Rates("JPY", 110.50);

        // Assert
        assertEquals(eurRates.getCurrencyCode(), "EUR");
        assertEquals(gbpRates.getCurrencyCode(), "GBP");
        assertEquals(jpyRates.getCurrencyCode(), "JPY");
        assertEquals(eurRates.getRate(), 1.10);
        assertEquals(gbpRates.getRate(), 0.85);
        assertEquals(jpyRates.getRate(), 110.50);
    }

    /**
     * Test 8: FixerApiClient initialized with different API keys
     * Verifies client creation with various API key formats
     */
    @Test
    public void testFixerApiClientWithDifferentApiKeys() {
        // Act
        FixerApiClient client1 = new FixerApiClient("key1");
        FixerApiClient client2 = new FixerApiClient("key2");
        FixerApiClient client3 = new FixerApiClient("");

        // Assert
        assertNotNull(client1);
        assertNotNull(client2);
        assertNotNull(client3);
    }

    /**
     * Test 9: Response structure with null rates field
     * Verifies handling of response creation with minimal data
     */
    @Test
    public void testLatestRatesResponseWithNullRates() {
        // Act
        LatestRatesResponse response = new LatestRatesResponse(
                TEST_BASE_CURRENCY,
                TEST_DATE,
                null
        );

        // Assert
        assertEquals(response.getBase(), TEST_BASE_CURRENCY);
        assertEquals(response.getDate(), TEST_DATE);
        assertNull(response.getRates());
    }

    /**
     * Test 10: Rates class with zero rate value
     * Verifies Rates handles zero exchange rates
     */
    @Test
    public void testRatesWithZeroValue() {
        // Act
        Rates rates = new Rates("USD", 0.0);

        // Assert
        assertEquals(rates.getRate(), 0.0);
        assertEquals(rates.getCurrencyCode(), "USD");
    }

    /**
     * Test 11: Rates class with negative rate value
     * Verifies Rates handles negative values (edge case)
     */
    @Test
    public void testRatesWithNegativeValue() {
        // Act
        Rates rates = new Rates("TEST", -1.5);

        // Assert
        assertEquals(rates.getRate(), -1.5);
        assertEquals(rates.getCurrencyCode(), "TEST");
    }

    /**
     * Test 12: Response with special characters in currency code
     * Verifies handling of unusual currency formats
     */
    @Test
    public void testRatesWithSpecialCharacterCurrency() {
        // Act
        Rates rates = new Rates("USD_ALT", 1.5);

        // Assert
        assertEquals(rates.getCurrencyCode(), "USD_ALT");
        assertEquals(rates.getRate(), 1.5);
    }
}
