package com.springboot3.sample.services.payment.service;

import com.springboot3.sample.services.common.api.MerchantApiSignature;
import com.springboot3.sample.services.payment.service.fallback.MerchantFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "merchant-service",url="http://google.com", fallback = MerchantFallback.class)
public interface MerchantClient extends MerchantApiSignature {
}
