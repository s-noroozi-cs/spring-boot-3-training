package com.springboot3.sample.graphql.repository;

import com.springboot3.sample.graphql.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity,Long> {
}
