package com.springboot3.sample.services.payment;

import com.springboot3.sample.services.common.config.CustomHttpHeaders;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class PaymentServiceApplicationTests {
    private List<String> solutions = Arrays.stream(
                    "A,B,C,D".split(","))
            .toList();
    @LocalServerPort
    int port;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @BeforeEach
    void reset_circuit_breaker() {
        circuitBreakerRegistry
                .getAllCircuitBreakers()
                .forEach(CircuitBreaker::reset);
    }

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
        Assertions.assertEquals(503, response.statusCode());

        for (String solution : solutions) {
            response = makeRequest(solution);
            log.info("with fallback solution %s, expected status 200"
                    .formatted(solution));
            Assertions.assertEquals(200, response.statusCode());
        }
    }

    private void checkHttpResponse(HttpResponse response,
                                   int statusCode,
                                   String expectedExceptionName) {
        String exceptionNameValue = response.headers()
                .firstValue(CustomHttpHeaders.EXCEPTION_NAME)
                .get();
        Assertions.assertEquals(statusCode, response.statusCode());
        Assertions.assertEquals(expectedExceptionName, exceptionNameValue);
    }

    @Test
    void test_circuit_breaker_without_fallback() {
        int serviceUnavailableStatusCode = 503;

        //to reproduce call not permit, should be at least one fail
        checkHttpResponse(makeRequest("---"),
                serviceUnavailableStatusCode,
                UnknownHostException.class.getName());

        var loopCount = 10;
        while (--loopCount > 0) {
            checkHttpResponse(makeRequest("---"),
                    serviceUnavailableStatusCode,
                    CallNotPermittedException.class.getName());
        }
    }

    @Test
    void test_circuit_breaker_with_fallback() {
        String solution = solutions.stream().findAny().get();
        HttpResponse response = makeRequest(solution);
        Assertions.assertEquals(200,response.statusCode());

        var loopCount = 10;
        while (--loopCount > 0) {
            solution = solutions.stream().findAny().get();
            response = makeRequest(solution);
            Assertions.assertEquals(503,response.statusCode());
        }
    }
}
