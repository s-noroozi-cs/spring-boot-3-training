package com.springboot3.sample.graphql.repository;

import com.springboot3.sample.graphql.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity,Long> {

}
