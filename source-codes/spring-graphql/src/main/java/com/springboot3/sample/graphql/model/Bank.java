package com.springboot3.sample.graphql.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bank {
    private String name;
    private String bic;
}
