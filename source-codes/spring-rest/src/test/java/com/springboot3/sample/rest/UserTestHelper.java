package com.springboot3.sample.rest;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpRequest;

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
}
