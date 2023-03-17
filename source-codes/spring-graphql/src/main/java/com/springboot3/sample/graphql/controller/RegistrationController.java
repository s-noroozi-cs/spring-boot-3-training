package com.springboot3.sample.graphql.controller;

import com.springboot3.sample.graphql.model.Register;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RegistrationController {
    @MutationMapping
    public Register register(@Argument String name, @Argument String mobile) {

        if(name == null || name.trim().length() < 2)
            throw new IllegalArgumentException("name is not valid");

        if(mobile == null || !mobile.matches("0[1-9][0-9]{9}"))
            throw new IllegalArgumentException("mobile is not valid");

        return new Register(name, mobile);
    }
}
