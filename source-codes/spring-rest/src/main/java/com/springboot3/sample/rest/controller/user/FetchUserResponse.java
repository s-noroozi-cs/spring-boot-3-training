package com.springboot3.sample.rest.controller.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FetchUserResponse {
    private long id;
    private String name;
    private String address;
    private String username;
    private LocalDateTime creationTime;
    private LocalDateTime lastModificationTime;
}
