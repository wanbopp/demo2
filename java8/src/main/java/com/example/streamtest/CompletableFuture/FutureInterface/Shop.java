package com.example.streamtest.CompletableFuture.FutureInterface;

import com.example.streamtest.CompletableFuture.Discount;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
    static Random random = new Random();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Shop(String name) {
        this.name = name;
    }

    private String name;
    private String price;


    //模拟延迟
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //模拟生成随机延时
    public static void randomDelay() {
        int delay = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static double calculatePrice(String product) {
        delay();
        return Math.random() * product.charAt(0) + product.charAt(1);
    }

    //同步的价格查询方法
    public static double getPrice(String product) {
        return calculatePrice(product);
    }

    public static String getPriceWithDiscount(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[(int) (Math.random() * Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", product, price, code);
    }


    //异步的价格查询方法
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> future = new CompletableFuture<>();//创建CompletableFurue对象，它会包含计算的结果
        //在另外的线程中以异步的方式进行计算
        try {
            new Thread(() -> {
                double price = calculatePrice(product);
                throw new RuntimeException("product not available");
//                future.complete(price);//需要长时间计算的任务结束并得出结果时，设置Future的返回值
            }).start();
        } catch (Exception e) {
            future.completeExceptionally(e);//失败时抛出异常，
        }
        return future;//无需等待还没结束的计算，直接返回Future对象
    }


    //使用工厂方法supplyAsync创建CompletableFuture对象
    public Future<Double> getPriceAsync1(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    public String getPriceStr2(String product) {
        double price = calculatePrice1(product);
        Discount.Code code = Discount.Code.values()[
                random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    //对最佳价格查询器的优化
    //1、加入随机延时
    //2、查询多个商店
    //3、使用折扣服务
    public String getPriceStrWithRandom(String product) {
        double v = calculatePrice2Random(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, v, code);
    }


    private double calculatePrice1(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    private double calculatePrice2Random(String product) {
        randomDelay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    //接受产品名，返回商电名称和价格
    public String getPriceStr(String product) {
        double price = calculatePrice(product);
        return String.format("%s:%.2f", name, price);
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


        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Integer reduce = integers.stream().reduce(0, Integer::sum);

        integers.stream().mapToInt(Integer::intValue)
                .sum();

    }

    private static void doSomethingElse() {
    }
}
