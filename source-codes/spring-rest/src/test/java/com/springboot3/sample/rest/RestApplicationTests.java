package com.springboot3.sample.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot3.sample.rest.controller.user.UserCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public abstract class RestApplicationTests {
    protected final String createUserPath = "/api/v1/users";

    @LocalServerPort
    private int port;

    protected String makeRequestUrl(String path) {
        return "http://localhost:%d%s".formatted(port, path);
    }

    protected void logHttpResponse(HttpResponse<String> response) {
        log.info("""
                
                --- response ---
                status code: %d
                headers: %s
                body: %s
                """.formatted(response.statusCode()
                , response.headers().map()
                , response.body()));
    }

}
