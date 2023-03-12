package com.springboot3.sample.graphql.repository;

import com.springboot3.sample.graphql.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
}
