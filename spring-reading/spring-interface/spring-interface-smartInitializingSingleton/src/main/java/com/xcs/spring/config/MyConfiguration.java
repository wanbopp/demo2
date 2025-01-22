package com.xcs.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xcs
 * @date 2023年09月19日 16时35分
 **/
@Configuration
@ComponentScan("com.xcs.spring.service")
public class MyConfiguration {

    @Bean
    public static MySmartInitializingSingleton mySmartInitializingSingleton(){
        return new MySmartInitializingSingleton();
    }
}
