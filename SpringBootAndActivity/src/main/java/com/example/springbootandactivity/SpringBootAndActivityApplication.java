package com.example.springbootandactivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.activiti.spring.boot.SecurityAutoConfiguration;
@SpringBootApplication(exclude=SecurityAutoConfiguration.class)
public class SpringBootAndActivityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAndActivityApplication.class, args);
	}

}
