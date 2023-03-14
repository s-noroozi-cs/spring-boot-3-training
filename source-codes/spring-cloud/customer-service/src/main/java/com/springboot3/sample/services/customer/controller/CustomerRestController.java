package com.springboot3.sample.services.customer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    @GetMapping("/{id}")
    public ResponseEntity getCustomer(@PathVariable("id") String id){
        return ResponseEntity.ok("customer with id: " + id);
    }
}
