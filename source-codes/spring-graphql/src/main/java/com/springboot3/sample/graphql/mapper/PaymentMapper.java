package com.springboot3.sample.graphql.mapper;

import com.springboot3.sample.graphql.entity.AccountEntity;
import com.springboot3.sample.graphql.entity.PaymentEntity;
import com.springboot3.sample.graphql.entity.PersonEntity;
import com.springboot3.sample.graphql.model.Account;
import com.springboot3.sample.graphql.model.Payment;
import com.springboot3.sample.graphql.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface PaymentMapper {
    @Mapping(source = "payerPersonId", target = "payer", qualifiedByName = "makeNewPerson")
    @Mapping(source = "payeePersonId", target = "payee", qualifiedByName = "makeNewPerson")
    Payment toModel(PaymentEntity entity);

    @Named("makeNewPerson")
    default Person makeNewPerson(long personId) {
        return Person.builder().id(personId).build();
    }

    Person toModel(PersonEntity entity);

    Account toModel(AccountEntity entity);

}
