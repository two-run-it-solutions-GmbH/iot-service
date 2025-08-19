package com.example.iot.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.iot.service.client")
public class IotServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotServiceApplication.class, args);
	}

}
