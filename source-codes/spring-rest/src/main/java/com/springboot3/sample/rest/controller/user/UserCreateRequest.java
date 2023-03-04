package com.springboot3.sample.rest.controller.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest {
    @NotNull(message = "name should not be null")
    @Size(min = 2, message = "name should have at least 2 characters")
    private String name;

    private String address;

    @NotNull(message = "username should not be null")
    @Size(min = 4, message = "username should have at least 4 characters")
    private String username;

    @NotNull(message = "password should not be null")
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;
}
