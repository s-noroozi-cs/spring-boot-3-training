package com.springboot3.sample.services.payment.service.fallback;

import com.springboot3.sample.services.payment.service.CustomerClient;
import org.springframework.http.ResponseEntity;

public class CustomerClientFallback implements CustomerClient {
    @Override
    public ResponseEntity<String> fetchCustomer(long id) {
        return ResponseEntity.ok(
                "--- fallback fetchCustomer, id: " + id + " ---");
    }
}
