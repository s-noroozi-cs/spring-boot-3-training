package com.springboot3.sample.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FetchUserTests extends UserTestHelper {

    @Test
    void fetchUserNotFoundExceptionTest() throws Exception {
        var userId = 0L;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        makeRequestUrl(fetchUserPath.formatted(userId))))
                .GET()
                .build();

        HttpResponse response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        logHttpResponse(response);

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value()
                , response.statusCode());
    }

    @Test
    void fetchUserTest() throws Exception {
        var userId = createUser();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        makeRequestUrl(fetchUserPath.formatted(userId))))
                .GET()
                .build();

        HttpResponse response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        logHttpResponse(response);

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
    }
}
