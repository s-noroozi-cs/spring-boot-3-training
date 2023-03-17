package com.springboot3.sample.services.common.api;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CustomerApiSignature {

    @GetMapping("/api/v1/customers/{id}")
    @CircuitBreaker(name = "customer-service")
    ResponseEntity<String> fetchCustomer(@PathVariable("id") long id);
}
