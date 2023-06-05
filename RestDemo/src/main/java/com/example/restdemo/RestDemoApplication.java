package com.example.restdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestDemoApplication.class, args);
	}
	@Bean
	public RestTemplate getRestTemplate(ClientHttpRequestFactory factory){
		RestTemplate restTemplate = new RestTemplate(factory);
		return restTemplate;
	}
//	有时候我们还需要通过 ClientHttpRequestFactory 配置最大链接数，忽略SSL证书等，大家需要的时候可以自己查看代码设置。

	@Bean
	public ClientHttpRequestFactory getClientHttpRequestFactory(){
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(500);
		factory.setConnectTimeout(1500);
//		factory.setProxy();设置代理
		return factory;
	}

}
