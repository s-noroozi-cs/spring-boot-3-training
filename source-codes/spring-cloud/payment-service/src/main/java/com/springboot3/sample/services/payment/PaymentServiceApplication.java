package com.springboot3.sample.services.payment;

import feign.Capability;
//import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

//    @Bean
//    public Capability capability(final MeterRegistry registry) {
//        return new MicrometerCapability(registry);
//    }

}
