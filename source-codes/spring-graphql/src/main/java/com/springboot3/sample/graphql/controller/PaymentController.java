package com.springboot3.sample.graphql.controller;

import com.springboot3.sample.graphql.model.Payment;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PaymentController {

    @QueryMapping
    public List<Payment> fetchPayment() {
        return List.of();
    }
}
