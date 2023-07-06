package com.example.streamtest.CompletableFuture;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/4 19:26
 * @注释  汇率服务
 */
public class Exchange {


    //模拟汇率服务
    public static double getRate(Money source, Money target) {
        return 1.2;
    }

}
