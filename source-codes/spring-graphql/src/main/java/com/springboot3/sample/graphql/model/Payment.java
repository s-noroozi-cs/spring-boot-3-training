package com.springboot3.sample.graphql.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Payment {
    private Person payer;
    private Person payee;
    private BigDecimal amount;
    private LocalDateTime dateTime;
}
