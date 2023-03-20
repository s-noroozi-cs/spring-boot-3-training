package com.springboot3.sample.services.payment.controller;

import com.springboot3.sample.services.payment.service.CustomerClient;
import com.springboot3.sample.services.payment.service.MerchantClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private CustomerClient customerClient;
    private MerchantClient merchantClient;
    private RabbitTemplate rabbitTemplate;

    public PaymentController(CustomerClient customerClient
            , MerchantClient merchantClient
            , RabbitTemplate rabbitTemplate) {
        this.customerClient = customerClient;
        this.merchantClient = merchantClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity fetchPayment(@PathVariable("id") long id) {

//        var response = """
//                --- response ---
//                payment service: fetch payment %d was successfully.
//                customer service: %s
//                merchant service: %s
//                """.formatted(id
//                , customerClient.fetchCustomer(10).getBody()
//                , merchantClient.fetchMerchant(20).getBody());

        rabbitTemplate.convertAndSend("myQueue", "Hello, world!");

        return ResponseEntity.ok("ok");
    }
}
