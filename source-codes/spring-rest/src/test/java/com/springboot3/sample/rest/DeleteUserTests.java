package com.springboot3.sample.rest;

import com.springboot3.sample.rest.config.ApiKeys;
import com.springboot3.sample.rest.config.HeaderNames;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DeleteUserTests extends UserTestHelper {

    @Test
    @SneakyThrows
    void check_authentication_fail_api() {
        long userId = 0L;
        HttpRequest deleteUser = HttpRequest.newBuilder()
                .uri(URI.create(makeRequestUrl(deleteUserPath.formatted(userId))))
                .DELETE()
                .build();

        HttpResponse response = HttpClient.newBuilder().build()
                .send(deleteUser, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(401,response.statusCode());
    }

    @Test
    @SneakyThrows
    void check_authorization_fail_api()  {
        long userId = 0L;
        HttpRequest deleteUser = HttpRequest.newBuilder()
                .uri(URI.create(makeRequestUrl(deleteUserPath.formatted(userId))))
                .header(HeaderNames.AUTHORIZATION, ApiKeys.CREATE_USER_API_KEY)
                .DELETE()
                .build();

        HttpResponse response = HttpClient.newBuilder().build()
                .send(deleteUser, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(403,response.statusCode());
    }

    @Test
    @SneakyThrows
    void check_not_found_exception()  {
        long userId = 0L;
        HttpRequest deleteUser = HttpRequest.newBuilder()
                .uri(URI.create(makeRequestUrl(deleteUserPath.formatted(userId))))
                .header(HeaderNames.AUTHORIZATION, ApiKeys.DELETE_USER_API_KEY)
                .DELETE()
                .build();

        HttpResponse response = HttpClient.newBuilder().build()
                .send(deleteUser, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(404,response.statusCode());
    }

    @Test
    @SneakyThrows
    void remove_user_successfully()  {
        long userId = createUser();
        HttpRequest deleteUser = HttpRequest.newBuilder()
                .uri(URI.create(makeRequestUrl(deleteUserPath.formatted(userId))))
                .header(HeaderNames.AUTHORIZATION, ApiKeys.DELETE_USER_API_KEY)
                .DELETE()
                .build();

        HttpResponse response = HttpClient.newBuilder().build()
                .send(deleteUser, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(404,response.statusCode());
    }
}
