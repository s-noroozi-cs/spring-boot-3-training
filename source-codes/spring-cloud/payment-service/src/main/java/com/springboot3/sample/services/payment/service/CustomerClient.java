package com.springboot3.sample.services.payment.service;

import com.springboot3.sample.services.common.api.CustomerApiSignature;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

@FeignClient(name = "customer-service")
public interface CustomerClient extends CustomerApiSignature {

    default ResponseEntity<String> fetchCustomerFallback(long id) {
        return ResponseEntity.ok("---fallback---");
    }
}
