package com.example.streamtest.CompletableFuture;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/4 19:27
 * @注释 货币枚举
 */
public enum Money {

    EUR("EUR"),
    USD("USD");

    private String name;

    Money(String eur) {
        this.name = eur;
    }

    public String getName() {
        return name;
    }
}
