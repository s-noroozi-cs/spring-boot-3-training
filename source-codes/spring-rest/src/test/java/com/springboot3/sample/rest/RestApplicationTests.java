package com.springboot3.sample.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.http.HttpResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public abstract class RestApplicationTests {
    protected final String createUserPath = "/api/v1/users";
    protected final String fetchUserPath = "/api/v1/users/%d";

    @LocalServerPort
    private int port;

    protected String makeRequestUrl(String path) {
        return "http://localhost:%d%s".formatted(port, path);
    }

}
