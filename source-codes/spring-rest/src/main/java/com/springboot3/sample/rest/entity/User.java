package com.springboot3.sample.rest.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class    User {
    private long id;
    private String name;
    private String address;
    private String username;
    private String password;
    private LocalDateTime creationTime;
    private LocalDateTime lastModificationTime;
}
