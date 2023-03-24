package com.springboot3.sample.services.payment.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentInitRequest {
    private String payerPersonId;
    private String payeePersonId;
    private BigDecimal amount;
    private String reason;
}
