package com.example.currencyconverter.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ConversionRequest {
    @NotNull
    private String from;
    
    @NotNull
    private String to;
    
    @NotNull
    @Positive
    private Double amount;
}
