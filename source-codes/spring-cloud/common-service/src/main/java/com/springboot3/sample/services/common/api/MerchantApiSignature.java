package com.springboot3.sample.services.common.api;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface MerchantApiSignature {

    @GetMapping("/api/v1/merchants/{id}")
    @CircuitBreaker(name = "merchant-service")
    ResponseEntity<String> fetchMerchant(@PathVariable("id") long id);
}
