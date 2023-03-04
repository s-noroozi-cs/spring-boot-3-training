package com.springboot3.sample.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CreateUserTests extends RestApplicationTests {

    @Test
    void createUserUnsupportedMediaTypeTest() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(makeRequestUrl(createUserPath)))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), response.statusCode());
    }

    @Test
    void createUserBadRequestTest() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(makeRequestUrl(createUserPath)))
                .POST(HttpRequest.BodyPublishers.noBody())
                .header("content-type", "application/json")
                .build();

        HttpResponse response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());
    }

}
