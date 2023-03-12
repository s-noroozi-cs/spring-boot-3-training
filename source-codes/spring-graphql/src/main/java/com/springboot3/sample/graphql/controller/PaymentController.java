package com.springboot3.sample.graphql.controller;

import com.springboot3.sample.graphql.mapper.PaymentMapper;
import com.springboot3.sample.graphql.model.Account;
import com.springboot3.sample.graphql.model.Payment;
import com.springboot3.sample.graphql.model.Person;
import com.springboot3.sample.graphql.repository.AccountRepository;
import com.springboot3.sample.graphql.repository.PaymentRepository;
import com.springboot3.sample.graphql.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentRepository paymentRepository;
    private final PersonRepository personRepository;
    private final AccountRepository accountRepository;

    private static PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @QueryMapping
    public List<Payment> fetchPayments() {
        return paymentRepository.findAll(
                        Pageable.ofSize(10).withPage(0))
                .stream()
                .map(paymentMapper::toModel)
                .collect(Collectors.toList());
    }

    @SchemaMapping
    public Person payer(Payment payment) {
        return fetchPerson(payment.getPayer().getId());
    }

    @SchemaMapping
    public Person payee(Payment payment) {
        return fetchPerson(payment.getPayee().getId());
    }

    private Person fetchPerson(long personId) {
        return personRepository
                .findById(personId)
                .map(paymentMapper::toModel)
                .orElseGet(Person::new);
    }

    @SchemaMapping
    public List<Account> accounts(Person person) {
        return null;
    }

    @SchemaMapping
    public Account payerAccount(Payment payment) {
        return null;
    }

    @SchemaMapping
    public Account payeeAccount(Payment payment) {
        return null;
    }
}
