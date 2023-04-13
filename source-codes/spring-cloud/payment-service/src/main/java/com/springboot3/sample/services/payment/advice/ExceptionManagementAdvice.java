package com.springboot3.sample.services.payment.advice;


import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.UnknownHostException;

import static com.springboot3.sample.services.common.config.CustomHttpHeaders.EXCEPTION_NAME;

@ControllerAdvice
public class ExceptionManagementAdvice {

    @ExceptionHandler({CallNotPermittedException.class})
    public ResponseEntity handleCallNotPermittedException(CallNotPermittedException ex) {
        var status = HttpStatus.SERVICE_UNAVAILABLE;
        return ResponseEntity
                .status(status)
                .header(EXCEPTION_NAME, CallNotPermittedException.class.getName())
                .body(status.getReasonPhrase());
    }

    @ExceptionHandler({UnknownHostException.class})
    public ResponseEntity handleUnknownHostException(UnknownHostException ex) {
        var status = HttpStatus.SERVICE_UNAVAILABLE;
        return ResponseEntity
                .status(status)
                .header(EXCEPTION_NAME, UnknownHostException.class.getName())
                .body(status.getReasonPhrase());
    }

}
