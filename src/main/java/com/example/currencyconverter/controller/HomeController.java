package com.example.currencyconverter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Currency Converter API is running! Available endpoints:\n" +
               "1. GET /api/rates?base=USD (Get exchange rates)\n" +
               "2. POST /api/convert (Convert currency)";
    }
}
