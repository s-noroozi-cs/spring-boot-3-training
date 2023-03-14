package com.springboot3.sample.services.payment.service;

import com.springboot3.sample.services.common.api.MerchantApiSignature;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "merchant-service")
public interface MerchantClient extends MerchantApiSignature {
}
