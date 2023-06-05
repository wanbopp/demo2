package com.example.piccdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PiccDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiccDemoApplication.class, args);
	}

}
