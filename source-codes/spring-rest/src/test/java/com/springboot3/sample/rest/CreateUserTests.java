package com.springboot3.sample.rest;

import com.springboot3.sample.rest.config.ApiKeys;
import com.springboot3.sample.rest.config.HeaderNames;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CreateUserTests extends UserTestHelper {

    @Test
    void createUserUnsupportedMediaTypeTest() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(makeRequestUrl(createUserPath)))
                .POST(HttpRequest.BodyPublishers.noBody())
                .header(HeaderNames.AUTHORIZATION, ApiKeys.TEST_USER_API_KEY)
                .build();

        HttpResponse response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()
                , response.statusCode());
    }

    @Test
    void createUserBadRequestTest() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(makeRequestUrl(createUserPath)))
                .POST(HttpRequest.BodyPublishers.noBody())
                .header("content-type", "application/json")
                .header(HeaderNames.AUTHORIZATION, ApiKeys.TEST_USER_API_KEY)
                .build();

        HttpResponse response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value()
                , response.statusCode());
    }

    @Test
    void createUserMethodNotAllowedTest() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(makeRequestUrl(createUserPath)))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .header("content-type", "application/json")
                .header(HeaderNames.AUTHORIZATION, ApiKeys.TEST_USER_API_KEY)
                .build();

        HttpResponse response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value()
                , response.statusCode());
    }

    @Test
    void createUserBadRequestValidationTest() throws Exception {
        JSONObject body = new JSONObject();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(makeRequestUrl(createUserPath)))
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .header("content-type", "application/json")
                .header(HeaderNames.AUTHORIZATION, ApiKeys.TEST_USER_API_KEY)
                .build();

        HttpResponse response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value()
                , response.statusCode());
    }

    @Test
    void createValidUserTest() throws Exception {
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(makeValidCreateUserRequest(), HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(HttpStatus.CREATED.value()
                , response.statusCode());
        Assertions.assertTrue(response.headers()
                .firstValue("location")
                .isPresent());
        Assertions.assertTrue(response.body().isBlank());
    }

}
