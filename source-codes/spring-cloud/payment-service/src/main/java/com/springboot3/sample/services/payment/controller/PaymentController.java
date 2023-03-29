package com.springboot3.sample.services.payment.controller;

import com.springboot3.sample.services.common.config.CustomKafkaBindingNames;
import com.springboot3.sample.services.common.config.CustomMessageHeaders;
import com.springboot3.sample.services.payment.model.PaymentInitRequest;
import com.springboot3.sample.services.payment.service.CustomerClient;
import com.springboot3.sample.services.payment.service.MerchantClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final CustomerClient customerClient;
    private final MerchantClient merchantClient;
    private final StreamBridge streamBridge;


    @GetMapping("/{id}")
    public ResponseEntity fetchPayment(@PathVariable("id") long id) {

        var response = """
                --- response ---
                payment service: fetch payment %d was successfully.
                customer service: %s
                merchant service: %s
                """.formatted(id
                , customerClient.fetchCustomer(10).getBody()
                , merchantClient.fetchMerchant(20).getBody());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity initPayment(HttpServletRequest request, @RequestBody PaymentInitRequest paymentInitRequest) {

        String paymentTraceId = UUID.randomUUID().toString();

        Message msg = MessageBuilder.withPayload(paymentInitRequest)
                .setHeader(CustomMessageHeaders.PAYMENT_TRACE_ID,paymentTraceId)
                .setHeader(KafkaHeaders.KEY,UUID.randomUUID().toString().getBytes())
                .build();

        streamBridge.send(CustomKafkaBindingNames.PAYMENT_NOTIFICATION,msg );

        return ResponseEntity.created(URI.create(request.getRequestURL() + "/" + paymentTraceId)).build();
    }
}
