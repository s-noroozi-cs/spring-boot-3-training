package com.springboot3.sample.services.payment.service;

import com.springboot3.sample.services.common.api.MerchantApiSignature;
import com.springboot3.sample.services.payment.service.fallback.MerchantClientFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "merchant-service", fallback = MerchantClientFallback.class)
public interface MerchantClient extends MerchantApiSignature {
}
