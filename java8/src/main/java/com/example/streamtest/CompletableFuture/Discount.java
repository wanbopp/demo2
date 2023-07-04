package com.example.streamtest.CompletableFuture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/28 19:49
 * @注释 折扣服务
 */
public class Discount {

    //以枚举类型定义折扣代码
    public enum Code {

        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    //根据折扣代码计算折扣后的价格
    public static String applyDiscount(Quote quote) {
        //将折扣代码应用于商品最初的原始价格
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    //模拟Discount服务延时响应
    private static double apply(double price, Code code) {
        delay();
        return price * (100 - code.percentage) / 100;
    }

    //模拟延迟
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
