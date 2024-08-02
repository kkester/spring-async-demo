package com.example.asyncdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AsyncDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncDemoApplication.class, args);
	}

}
