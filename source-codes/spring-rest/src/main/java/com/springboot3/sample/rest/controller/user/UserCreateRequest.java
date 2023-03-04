package com.springboot3.sample.rest.controller.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest {
    @NotBlank(message = "user name should not be blank")
    private String name;
    private String address;
    private String username;
    private String password;
}
