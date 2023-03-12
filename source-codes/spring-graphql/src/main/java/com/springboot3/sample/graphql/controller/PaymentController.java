package com.springboot3.sample.graphql.controller;

import com.springboot3.sample.graphql.mapper.PaymentMapper;
import com.springboot3.sample.graphql.model.Account;
import com.springboot3.sample.graphql.model.Payment;
import com.springboot3.sample.graphql.model.Person;
import com.springboot3.sample.graphql.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private static PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @QueryMapping
    public List<Payment> fetchPayments() {
        return paymentService.fetchPaymentEntity(
                        Pageable.ofSize(10).withPage(0))
                .stream()
                .map(paymentMapper::toModel)
                .collect(Collectors.toList());
    }

    @SchemaMapping
    public Person payer(Payment payment) {
        return Person.builder()
                .name("test payer")
                .id((int) System.currentTimeMillis() % 100)
                .phone(LocalDateTime.now() + "")
                .build();
    }

    @SchemaMapping
    public Person payee(Payment payment) {
        return Person.builder()
                .name("test payee")
                .id((int) System.currentTimeMillis() % 100)
                .phone(LocalDateTime.now() + "")
                .build();
    }

    @SchemaMapping
    public List<Account> accounts(Person person) {
        return List.of();
    }
}
