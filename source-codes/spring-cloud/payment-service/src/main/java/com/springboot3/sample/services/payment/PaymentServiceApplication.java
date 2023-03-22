package com.springboot3.sample.services.payment;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class PaymentServiceApplication {

    @Autowired
    private StreamBridge bridge;

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    static class MyCorrelationData extends CorrelationData {

        private final String payload;

        MyCorrelationData(String id, String payload) {
            super(id);
            this.payload = payload;
        }

        public String getPayload() {
            return this.payload;
        }

    }

    @Scheduled(fixedDelay = 3000)
    public void produceMessage() {
        String message = "www.pkslow.com - " + LocalDateTime.now();
        System.out.println("Sending value: " + message);
        MyCorrelationData corr = new MyCorrelationData(UUID.randomUUID().toString(), message);
        this.bridge.send("pkslowSource", MessageBuilder.withPayload(message)
//                .setHeader(AmqpHeaders.PUBLISH_CONFIRM_CORRELATION, corr)
                .build());
//		bridge.send("pkslowSource",message);
    }

//	@Bean
//	public Supplier<String> pkslowSource() {
//		return () -> {
//			String message = "www.pkslow.com - " + LocalDateTime.now();
//			System.out.println("Sending value: " + message);
//			return message;
//		};
//	}


//    @Bean
//    public Consumer<String> pkslowSink() {
//        return message -> {
//            System.out.println("Received message " + message);
//        };
//    }
}
