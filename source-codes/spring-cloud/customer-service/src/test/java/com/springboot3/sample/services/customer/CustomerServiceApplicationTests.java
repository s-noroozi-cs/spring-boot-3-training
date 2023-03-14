package com.springboot3.sample.services.customer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerServiceApplicationTests {
    @Test
    void context_load(){
        System.out.println("spring context load successfully.");
    }
}
