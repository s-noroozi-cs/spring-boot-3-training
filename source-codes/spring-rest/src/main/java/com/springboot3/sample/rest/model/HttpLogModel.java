package com.springboot3.sample.rest.model;

import com.google.gson.Gson;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public class HttpLogModel {
    private String url;
    private String uri;
    private String method;
    private Map<String, List<String>> requestHeaders;
    private String requestBody;
    private Integer statusCode;
    private Map<String, List<String>> responseHeaders;
    private String responseBody;
    private String exceptionMsg;
    private long elapseTime;

    public String asJsonString() {
        return new Gson().toJson(this);
    }

    public boolean hasError() {
        return exceptionMsg != null && !exceptionMsg.isBlank();
    }
}
