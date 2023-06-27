package com.example.streamtest.CompletableFuture.FutureInterface;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/26 19:04
 * @注释
 */

public class Shop {
    public Shop(String name) {
    }

    //同步的价格查询方法
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    private static double calculatePrice(String product) {
        delay();
        return Math.random() * product.charAt(0) + product.charAt(1);
    }


    //模拟延迟
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    //异步的价格查询方法
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> future = new CompletableFuture<>();//创建CompletableFurue对象，它会包含计算的结果
        //在另外的线程中以异步的方式进行计算
        try {
            new Thread(() -> {
                double price = calculatePrice(product);
                future.complete(price);//需要长时间计算的任务结束并得出结果时，设置Future的返回值
            }).start();
        } catch (Exception e) {
            future.completeExceptionally(e);//失败时抛出异常，
        }
        return future;//无需等待还没结束的计算，直接返回Future对象
    }


    public static void main(String[] args) {
        //使用异步API
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> future = shop.getPriceAsync("my favorite product");
        long l = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Invocation returned after " + l + " msecs");//返回Future对象的时间
        doSomethingElse();

        try {
            //从future对象中读取价格，如果价格未知，会发生阻塞
            Double aDouble = future.get();
            System.out.println("Price is " + aDouble);

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        long l1 = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Price returned after " + l1 + " msecs");//计算完成的时间

    }

    private static void doSomethingElse() {
    }
}
