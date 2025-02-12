package com.example.currencyconverter.service;

import com.example.currencyconverter.model.ConversionRequest;
import com.example.currencyconverter.model.ConversionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExchangeRateService {

    private final RestTemplate restTemplate;
    private static final String API_BASE_URL = "https://open.er-api.com/v6/latest/";

    public ExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getRates(String base) {
        String url = API_BASE_URL + base;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return response.getBody();
    }

    public ConversionResponse convertCurrency(ConversionRequest request) {
        Map<String, Object> rates = getRates(request.getFrom());
        Map<String, Double> ratesMap = (Map<String, Double>) rates.get("rates");
        
        if (!ratesMap.containsKey(request.getTo())) {
            throw new IllegalArgumentException("Invalid target currency: " + request.getTo());
        }
        
        Double rate = ratesMap.get(request.getTo());
        Double convertedAmount = request.getAmount() * rate;
        
        return ConversionResponse.builder()
                .from(request.getFrom())
                .to(request.getTo())
                .amount(request.getAmount())
                .convertedAmount(convertedAmount)
                .build();
    }
}
