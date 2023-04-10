package com.springboot3.sample.services.payment.service.fallback;

import com.springboot3.sample.services.common.api.MerchantApiSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MerchantFallback implements MerchantApiSignature {
    @Override
    public ResponseEntity<String> fetchMerchant(long id) {
        return ResponseEntity.ok("---fallback---");
    }
}
