package com.springboot3.sample.services.payment.controller;

import com.springboot3.sample.services.common.config.CustomKafkaBindingNames;
import com.springboot3.sample.services.common.config.CustomMessageHeaders;
import com.springboot3.sample.services.payment.model.PaymentInitRequest;
import com.springboot3.sample.services.payment.service.CustomerClient;
import com.springboot3.sample.services.payment.service.MerchantClient;
import com.springboot3.sample.services.payment.service.fallback.CustomerClientFallback;
import com.springboot3.sample.services.payment.service.fallback.MerchantClientFallback;
import com.springboot3.sample.services.payment.util.FeignFallbackUtil;
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

    private String solution(long id) {
        return """
                --- response ---
                payment service: fetch payment %d was successfully.
                customer service: %s
                merchant service: %s
                """.formatted(id
                , customerClient.fetchCustomer(id).getBody()
                , merchantClient.fetchMerchant(id).getBody()
        );
    }

    private String solutionA(long id) {
        return """
                --- response ---
                payment service: fetch payment %d was successfully.
                customer service: %s
                merchant service: %s
                """.formatted(id
                , FeignFallbackUtil.call(
                        () -> customerClient.fetchCustomer(id).getBody()
                        , () -> new CustomerClientFallback().fetchCustomer(id).getBody()
                )

                , FeignFallbackUtil.call(
                        () -> merchantClient.fetchMerchant(id).getBody(),
                        () -> new MerchantClientFallback().fetchMerchant(id).getBody()
                )
        );
    }

    private String solutionB(long id) {
        CustomerClient customerProxy = FeignFallbackUtil
                .makeCustomerProxy(customerClient, new CustomerClientFallback());
        MerchantClient merchantProxy = FeignFallbackUtil
                .makeMerchantProxy(merchantClient, new MerchantClientFallback());

        return """
                --- response ---
                payment service: fetch payment %d was successfully.
                customer service: %s
                merchant service: %s
                """.formatted(id
                , customerProxy.fetchCustomer(id).getBody()
                , merchantProxy.fetchMerchant(id).getBody()
        );
    }

    private String solutionC(long id) {
        CustomerClient customerProxy = FeignFallbackUtil.makeCustomerProxy(customerClient);
        MerchantClient merchantProxy = FeignFallbackUtil.makeMerchantProxy(merchantClient);

        return """
                --- response ---
                payment service: fetch payment %d was successfully.
                customer service: %s
                merchant service: %s
                """.formatted(id
                , customerProxy.fetchCustomer(id).getBody()
                , merchantProxy.fetchMerchant(id).getBody()
        );
    }

    private String solutionD(long id) {
        CustomerClient customerProxy = FeignFallbackUtil
                .makeProxy(CustomerClient.class, customerClient);
        MerchantClient merchantProxy = FeignFallbackUtil
                .makeProxy(MerchantClient.class, merchantClient);

        return """
                --- response ---
                payment service: fetch payment %d was successfully.
                customer service: %s
                merchant service: %s
                """.formatted(id
                , customerProxy.fetchCustomer(id).getBody()
                , merchantProxy.fetchMerchant(id).getBody()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity fetchPayment(@PathVariable("id") long id,
                                       @RequestHeader("x-solution") String solution) {
        if ("A".equalsIgnoreCase(solution))
            return ResponseEntity.ok(solutionA(id));

        if ("B".equalsIgnoreCase(solution))
            return ResponseEntity.ok(solutionB(id));

        if ("C".equalsIgnoreCase(solution))
            return ResponseEntity.ok(solutionC(id));

        if ("D".equalsIgnoreCase(solution))
            return ResponseEntity.ok(solutionD(id));

        return ResponseEntity.ok(solution(id));
    }

    @PostMapping
    public ResponseEntity initPayment(HttpServletRequest request, @RequestBody PaymentInitRequest paymentInitRequest) {

        String paymentTraceId = UUID.randomUUID().toString();

        Message msg = MessageBuilder.withPayload(paymentInitRequest)
                .setHeader(CustomMessageHeaders.PAYMENT_TRACE_ID, paymentTraceId)
                .setHeader(KafkaHeaders.KEY, UUID.randomUUID().toString().getBytes())
                .build();

        streamBridge.send(CustomKafkaBindingNames.PAYMENT_NOTIFICATION, msg);

        return ResponseEntity.created(URI.create(request.getRequestURL() + "/" + paymentTraceId)).build();
    }
}
