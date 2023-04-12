package com.springboot3.sample.services.payment.service.fallback;

import com.springboot3.sample.services.payment.service.MerchantClient;
import org.springframework.http.ResponseEntity;

public class MerchantClientFallback implements MerchantClient {

    @Override
    public ResponseEntity<String> fetchMerchant(long id){
        return ResponseEntity.ok(
                "--- fallback fetchMerchant, id: " + id + " ---");
    }
}
