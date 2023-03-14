package com.springboot3.sample.services.merchant.controller;

import com.springboot3.sample.services.common.api.MerchantApiSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MerchantRestController implements MerchantApiSignature {

    @Override
    public ResponseEntity fetchMerchant(long id) {
        return ResponseEntity
                .ok("load merchant with %d successfully."
                        .formatted(id));
    }
}
