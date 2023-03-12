package com.springboot3.sample.graphql.entity;

import com.springboot3.sample.graphql.model.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long payerPersonId;
    private long payerAccountId;
    private long payeePersonId;
    private long payeeAccountId;
    private BigDecimal amount;
    private LocalDateTime dateTime;
}
