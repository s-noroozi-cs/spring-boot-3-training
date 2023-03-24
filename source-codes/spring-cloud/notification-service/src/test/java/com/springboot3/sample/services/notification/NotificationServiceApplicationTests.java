package com.springboot3.sample.services.notification;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotificationServiceApplicationTests {
    @Test
    void context_load(){
        System.out.println("spring context load successfully.");
    }
}
