package com.springboot3.sample.graphql.service;

import com.springboot3.sample.graphql.entity.PaymentEntity;
import com.springboot3.sample.graphql.repository.AccountRepository;
import com.springboot3.sample.graphql.repository.PaymentRepository;
import com.springboot3.sample.graphql.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final PersonRepository personRepository;

    public Page<PaymentEntity> fetchPaymentEntity(Pageable pageable){
        return paymentRepository.findAll(pageable);
    }

}
