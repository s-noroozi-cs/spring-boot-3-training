package com.springboot3.sample.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Register {
    private final String id = UUID.randomUUID().toString();
    private String name;
    private String mobile;
}
