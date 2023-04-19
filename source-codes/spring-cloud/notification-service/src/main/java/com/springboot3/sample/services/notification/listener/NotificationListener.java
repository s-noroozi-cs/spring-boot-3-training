package com.springboot3.sample.services.notification.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NotificationListener {

    @Bean
    public Consumer<Message<byte[]>> notification() {
        return message -> {
//            try {
//                Thread.sleep(2000L);
//            }catch (Throwable ex){
//                log.error(ex.getMessage(),ex);
//            }
            log.info("Received new message, payload:{}, headers: {}",
                    new String(message.getPayload(), StandardCharsets.UTF_8),
                    message.getHeaders()
                            .entrySet()
                            .stream()
                            .map(i -> String.format("%s: %s", i.getKey(), i.getValue()))
                            .collect(Collectors.joining(", ","[","]"))
            );
        };
    }
}
