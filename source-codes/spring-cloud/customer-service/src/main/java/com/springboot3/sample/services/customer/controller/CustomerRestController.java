package com.springboot3.sample.services.customer.controller;

import com.springboot3.sample.services.common.api.CustomerApiSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController implements CustomerApiSignature {

    @Override
    public ResponseEntity fetchCustomer(long id) {
        return ResponseEntity
                .ok("load customer with %d successfully."
                        .formatted(id));
    }
}
