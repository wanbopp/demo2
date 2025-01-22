package com.xcs.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xcs
 * @date 2023年09月19日 16时35分
 **/
@Configuration
public class MyConfiguration {

    @Bean
    public static MyDisposableBean myDisposableBean(){
        return new MyDisposableBean();
    }
}
