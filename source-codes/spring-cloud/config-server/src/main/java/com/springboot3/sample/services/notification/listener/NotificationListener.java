package com.springboot3.sample.services.notification.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class NotificationListener {

    @Bean
    public Consumer<Message> notification() {
        return message -> {
            log.info("Received new message " + message);
        };
    }
}
