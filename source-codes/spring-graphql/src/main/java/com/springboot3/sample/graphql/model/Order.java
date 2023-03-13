package com.springboot3.sample.graphql.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Order {
    private final String id = UUID.randomUUID().toString();
    private String name;
    private int count;
}
