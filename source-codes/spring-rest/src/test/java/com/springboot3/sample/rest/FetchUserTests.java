package com.springboot3.sample.rest;

import com.springboot3.sample.rest.config.ApiKeys;
import com.springboot3.sample.rest.config.HeaderNames;
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
                .header(HeaderNames.AUTHORIZATION, ApiKeys.TEST_USER_API_KEY)
                .build();

        HttpResponse response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

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
                .header(HeaderNames.AUTHORIZATION, ApiKeys.TEST_USER_API_KEY)
                .build();

        HttpResponse response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(HttpStatus.OK.value(), response.statusCode());
    }
}
