package com.springboot3.sample.services.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class MerchantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchantServiceApplication.class, args);
	}


	@Bean
    public Consumer<String> pkslowSink() {
        return message -> {
            System.out.println("Received message " + message);
        };
    }

}
