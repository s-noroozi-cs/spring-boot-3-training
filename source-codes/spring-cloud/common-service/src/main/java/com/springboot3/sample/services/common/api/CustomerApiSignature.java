package com.springboot3.sample.services.common.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CustomerApiSignature {

    @GetMapping("/api/v1/customers/{id}")
    ResponseEntity fetchCustomer(@PathVariable("id") long id);
}
