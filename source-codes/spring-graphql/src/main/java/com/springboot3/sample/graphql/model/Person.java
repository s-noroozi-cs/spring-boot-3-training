package com.springboot3.sample.graphql.model;

import lombok.Data;

import java.util.List;

@Data
public class Person {
    private int id;
    private String name;
    private String address;
    private String phone;
    private List<Account> accounts;
}
