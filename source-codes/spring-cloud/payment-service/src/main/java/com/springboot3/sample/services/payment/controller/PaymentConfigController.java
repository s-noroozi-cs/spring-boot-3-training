package com.springboot3.sample.services.payment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment-config")
@RefreshScope
public class PaymentConfigController {
    @Value("${payment.fee}")
    private double paymentFee;

    @GetMapping("/fee")
    public double getFee() {
        return paymentFee;
    }
}
