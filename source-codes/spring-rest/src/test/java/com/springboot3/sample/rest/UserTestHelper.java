package com.springboot3.sample.rest;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class UserTestHelper extends RestApplicationTests {

    protected HttpRequest makeValidCreateUserRequest() throws Exception {
        JSONObject body = new JSONObject();
        body.put("name", "test");
        body.put("address", "tehran");
        body.put("username", "t140");
        body.put("password", "40302010");

        return HttpRequest.newBuilder()
                .uri(URI.create(makeRequestUrl(createUserPath)))
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .header("content-type", "application/json")
                .build();
    }

    protected long createUser() throws Exception {
        HttpResponse response = HttpClient.newHttpClient()
                .send(makeValidCreateUserRequest(), HttpResponse.BodyHandlers.ofString());

        String locationHeader = response.headers()
                .firstValue(HttpHeaders.LOCATION)
                .orElse("");

        Assertions.assertEquals(HttpStatus.CREATED.value()
                , response.statusCode());

        Assertions.assertFalse(locationHeader.isBlank());
        String userId = locationHeader.substring(
                locationHeader.lastIndexOf("/") + 1);
        return Long.parseLong(userId);
    }
}
