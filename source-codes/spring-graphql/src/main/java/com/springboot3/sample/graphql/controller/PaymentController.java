package com.springboot3.sample.graphql.controller;

import com.springboot3.sample.graphql.model.Account;
import com.springboot3.sample.graphql.model.Payment;
import com.springboot3.sample.graphql.model.Person;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PaymentController {

    @QueryMapping
    public List<Payment> fetchPayments() {
        Payment payment = Payment.builder()
                .payer(Person.builder().name("payer name").build())
                .payee(Person.builder().name("payee name").build())
                .amount(BigDecimal.valueOf(100))
                .dateTime(LocalDateTime.now())
                .build();
        return List.of(payment);
    }

    @SchemaMapping
    public Person payer(Payment payment){
        return Person.builder()
                .name("test payer")
                .id((int)System.currentTimeMillis() % 100)
                .phone(LocalDateTime.now() + "")
                .build();
    }

    @SchemaMapping
    public Person payee(Payment payment){
        return Person.builder()
                .name("test payee")
                .id((int)System.currentTimeMillis() % 100)
                .phone(LocalDateTime.now() + "")
                .build();
    }

    @SchemaMapping
    public List<Account> accounts(Person person){
        return List.of();
    }
}
