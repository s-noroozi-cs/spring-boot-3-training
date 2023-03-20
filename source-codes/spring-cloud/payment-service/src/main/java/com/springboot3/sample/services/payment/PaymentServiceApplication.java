package com.springboot3.sample.services.payment;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

	@Bean
	public Queue myQueue() {
		return new Queue("myQueue", false);
	}

	@RabbitListener(queues = "myQueue")
	public void listen(String in) {
		System.out.println("Message read from myQueue : " + in);
	}

}
