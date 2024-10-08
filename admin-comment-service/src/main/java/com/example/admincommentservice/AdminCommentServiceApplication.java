package com.example.admincommentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminCommentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminCommentServiceApplication.class, args);
	}

}