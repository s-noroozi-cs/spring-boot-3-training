package com.springboot3.sample.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    private Person payer;
    private Account payerAccount;
    private Person payee;
    private Account payeeAccount;
    private BigDecimal amount;
    private LocalDateTime dateTime;
}
