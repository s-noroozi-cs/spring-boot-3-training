package com.springboot3.sample.graphql.controller;

import com.springboot3.sample.graphql.model.Bank;
import jakarta.annotation.PostConstruct;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
public class BankController {
    Set<Bank> bankRepository = new CopyOnWriteArraySet<>();

    @PostConstruct
    void init() {
        bankRepository.add(Bank.builder()
                        .name("ملی")
                        .bic("MELIIRTHXXX").build());
        bankRepository.add(Bank.builder()
                        .name("صادرات")
                        .bic("BSIRIRTHXXX").build());
    }

    @Secured("ROLE_USER")
    @QueryMapping
    public Set<Bank> fetchAllBanks() {
        return bankRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @MutationMapping
    public Bank createBank(@Argument String name, @Argument String bic) {
        Bank bank = Bank.builder()
                .bic(bic)
                .name(name)
                .build();
        if (bankRepository.add(bank))
            return bank;
        else
            return null;
    }

}
