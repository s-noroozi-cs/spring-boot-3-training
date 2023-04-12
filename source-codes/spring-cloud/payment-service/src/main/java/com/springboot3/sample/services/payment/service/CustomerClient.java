package com.springboot3.sample.services.payment.service;

import com.springboot3.sample.services.common.api.CustomerApiSignature;
import com.springboot3.sample.services.payment.service.fallback.CustomerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "customer-service", fallback = CustomerClientFallback.class)
public interface CustomerClient extends CustomerApiSignature {
}
