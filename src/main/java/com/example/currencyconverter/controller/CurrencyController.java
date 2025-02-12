package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.ConversionRequest;
import com.example.currencyconverter.model.ConversionResponse;
import com.example.currencyconverter.service.ExchangeRateService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * Controller for handling currency conversion requests.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class CurrencyController {

    private final ExchangeRateService exchangeRateService;

    /**
     * Constructs a new CurrencyController instance.
     *
     * @param exchangeRateService the exchange rate service to use
     */
    public CurrencyController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * Retrieves the latest exchange rates for the specified base currency.
     *
     * @param base the base currency (defaults to USD)
     * @return a map of exchange rates
     */
    @GetMapping(value = "/rates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getRates(
            @RequestParam(defaultValue = "USD") String base) {
        try {
            Map<String, Object> rates = exchangeRateService.getRates(base);
            return ResponseEntity.ok(rates);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching exchange rates: " + e.getMessage());
        }
    }

    /**
     * Converts the specified amount of currency from one currency to another.
     *
     * @param request the conversion request
     * @return the conversion response
     */
    @PostMapping(value = "/convert", 
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConversionResponse> convertCurrency(
            @Valid @RequestBody ConversionRequest request) {
        try {
            ConversionResponse response = exchangeRateService.convertCurrency(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Error converting currency: " + e.getMessage());
        }
    }

    // Error fallback endpoint
    @RequestMapping(value = "/error")
    public String handleError() {
        return "An error occurred. Please make sure you're using the correct HTTP method and request format.";
    }
}
