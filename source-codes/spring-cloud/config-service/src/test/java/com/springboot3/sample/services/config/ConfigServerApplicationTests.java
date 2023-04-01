package com.springboot3.sample.services.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfigServerApplicationTests {
    @Test
    void context_load(){
        System.out.println("spring context load successfully.");
    }
}
