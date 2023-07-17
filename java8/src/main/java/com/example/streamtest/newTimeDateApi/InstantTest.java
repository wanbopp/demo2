package com.example.streamtest.newTimeDateApi;

import java.time.Instant;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/12 19:36
 * @注释 Instant机器时间格式 从1970-01-01T00:00:00Z开始计算
 */
public class InstantTest {

    public static void main(String[] args) {
        Instant instant = Instant.ofEpochSecond(3);
        System.out.println("instant = " + instant);
        //重载方法 支持在秒的基础上设置以纳秒为单位的偏移量
        Instant instant1 = Instant.ofEpochSecond(3, 1000000);
        System.out.println("instant1 = " + instant1);

    }
}
