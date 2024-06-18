package com.example.demo.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/4 9:34
 * @注释
 */
public class LocalDateTimeTest {
    public static void main(String[] args) {
        LocalDateTime of = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        System.out.println("of = " + of);
        LocalDateTime of1 = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        System.out.println("of1 = " + of1);
    }
}
