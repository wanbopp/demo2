package com.example.piccdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/4/21 14:27
 * @注释
 */
@Controller
@RequestMapping("asyncdownload")
public class AsyncDownload {

    @Autowired
    @Qualifier("async-pool")
    private ThreadPoolTaskExecutor executor;


    public static void main(String[] args) {
        String substring = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
        System.out.println("substring = " + substring);
    }



}
