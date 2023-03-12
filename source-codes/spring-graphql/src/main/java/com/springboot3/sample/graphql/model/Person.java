package com.springboot3.sample.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    private long id;
    private String name;
    private String address;
    private String phone;
    private Account account;
}
