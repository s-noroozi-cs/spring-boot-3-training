package com.springboot3.sample.services.common.api;

import org.springframework.http.ResponseEntity;

import static com.springboot3.sample.services.common.config.CustomHttpHeaders.RESPONSE_SOURCE;

public interface CommonApiSignature {
    default ResponseEntity<String> makeFallbackResponse(String body){
        return ResponseEntity
                .status(200)
                .header(RESPONSE_SOURCE, "fallback")
                .body(body);
    }
}
