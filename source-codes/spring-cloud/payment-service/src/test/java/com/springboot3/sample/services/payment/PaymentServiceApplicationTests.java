package com.springboot3.sample.services.payment;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class PaymentServiceApplicationTests {
    @LocalServerPort
    int port;

    @BeforeAll
    static void notifyInfraRequirementForTest() {
        log.warn("to execute all test pass, please start docker compose before it.");
    }

    @SneakyThrows
    private HttpResponse<String> makeRequest(String solution) {
        URI uri = URI.create(
                "http://localhost:%d/api/v1/payments/123"
                        .formatted(port));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("x-solution", solution)
                .GET()
                .build();

        return HttpClient.newHttpClient()
                .send(request,
                        HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void context_load() {
        System.out.println("spring context load successfully.");
    }

    @Test
    void test_fallback() {
        HttpResponse response = makeRequest("---");
        log.info("without fallback solution, expected status 500");
        Assertions.assertEquals(500, response.statusCode());

        for (String solution : "A,B,C,D".split(",")) {
            response = makeRequest(solution);
            log.info("with fallback solution %s, expected status 200"
                    .formatted(solution));
            Assertions.assertEquals(200, response.statusCode());
        }
    }

    @RepeatedTest(10)
    void test_circuit_breaker(){
        //should be
        HttpResponse response = makeRequest("---");
        log.info("""
                --- without fallback solution
                status code: %d
                body: %s
                """.formatted(response.statusCode(),response.body()));
    }
}
