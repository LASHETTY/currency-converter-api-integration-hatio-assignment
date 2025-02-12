package com.example.currencyconverter.service;

import com.example.currencyconverter.model.ConversionRequest;
import com.example.currencyconverter.model.ConversionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ExchangeRateServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exchangeRateService = new ExchangeRateService(restTemplate);
    }

    @Test
    void convertCurrency_Success() {
        // Prepare test data
        ConversionRequest request = new ConversionRequest();
        request.setFrom("USD");
        request.setTo("EUR");
        request.setAmount(100.0);

        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 0.85);
        apiResponse.put("rates", rates);

        // Mock external API call
        when(restTemplate.getForEntity(any(String.class), eq(Map.class)))
                .thenReturn(ResponseEntity.ok(apiResponse));

        // Test conversion
        ConversionResponse response = exchangeRateService.convertCurrency(request);

        // Verify results
        assertNotNull(response);
        assertEquals("USD", response.getFrom());
        assertEquals("EUR", response.getTo());
        assertEquals(100.0, response.getAmount());
        assertEquals(85.0, response.getConvertedAmount());
    }

    @Test
    void convertCurrency_InvalidCurrency() {
        // Prepare test data
        ConversionRequest request = new ConversionRequest();
        request.setFrom("USD");
        request.setTo("INVALID");
        request.setAmount(100.0);

        Map<String, Object> apiResponse = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        apiResponse.put("rates", rates);

        // Mock external API call
        when(restTemplate.getForEntity(any(String.class), eq(Map.class)))
                .thenReturn(ResponseEntity.ok(apiResponse));

        // Test invalid currency
        assertThrows(IllegalArgumentException.class, () -> 
            exchangeRateService.convertCurrency(request)
        );
    }
}
