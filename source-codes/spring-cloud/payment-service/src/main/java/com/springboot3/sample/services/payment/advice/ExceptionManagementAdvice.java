package com.springboot3.sample.services.payment.advice;


import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionManagementAdvice {

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity handleCallNotPermittedException(CallNotPermittedException ex) {
        var status = HttpStatus.TOO_MANY_REQUESTS;
        return ResponseEntity
                .status(status)
                .body(status.getReasonPhrase());
    }
}
