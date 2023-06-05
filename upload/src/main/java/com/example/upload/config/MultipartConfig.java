package com.example.upload.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;



@Configuration
public class MultipartConfig {
    @Bean
    MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        String property = System.getProperty("user.home" )+ "/my/temp";
        File file = new File(property);
        if (!file.exists()){
            file.mkdirs();
        }
        multipartConfigFactory.setLocation(property);
        multipartConfigFactory.setMaxFileSize(10000000000L);
        multipartConfigFactory.setMaxRequestSize(2000000L);
        return  multipartConfigFactory.createMultipartConfig();
    }
}
