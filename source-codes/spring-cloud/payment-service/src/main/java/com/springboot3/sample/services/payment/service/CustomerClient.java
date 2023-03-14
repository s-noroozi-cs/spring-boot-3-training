package com.springboot3.sample.services.payment.service;

import com.springboot3.sample.services.common.api.CustomerApiSignature;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "customer-service")
public interface CustomerClient extends CustomerApiSignature {
}
