package com.springboot3.sample.rest.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static Map<String, List<String>> extractHeaders(HttpServletRequest request) {
        Map<String, List<String>> map = new HashMap<>();
        request.getHeaderNames().asIterator()
                .forEachRemaining(name -> map.put(name, new ArrayList<>()));
        for (String key : map.keySet()) {
            List<String> items = map.get(key);
            request.getHeaders(key).asIterator().forEachRemaining(items::add);
        }
        return map;
    }

    public static Map<String, List<String>> extractHeaders(HttpServletResponse response) {
        Map<String, List<String>> map = new HashMap<>();
        response.getHeaderNames().forEach(name -> map.put(name, new ArrayList<>()));
        for (String key : map.keySet()) {
            List<String> items = map.get(key);
            response.getHeaders(key).forEach(items::add);
        }
        return map;
    }
}
