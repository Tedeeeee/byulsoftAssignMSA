package com.example.adminboardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminBoardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminBoardServiceApplication.class, args);
	}

}
