package com.springboot3.sample.rest.advice;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.springboot3.sample.rest.exception.ForbiddenException;
import com.springboot3.sample.rest.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return globalHandler(errors.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return globalHandler(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ForbiddenException.class)
    protected ResponseEntity<Object> handleForbiddenException(NotFoundException ex) {
        return globalHandler(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<Object> globalHandler(String data, HttpStatus status) {
        Map<String, String> body = new HashMap<>();
        body.put("statusCodeName", status.name());
        body.put("statusCodeValue", String.valueOf(status.value()));
        body.put("success", "false");
        body.put("errorMsg", data);
        return new ResponseEntity<>(body, new HttpHeaders(), status);
    }
}
