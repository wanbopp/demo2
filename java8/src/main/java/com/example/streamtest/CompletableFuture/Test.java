package com.example.streamtest.CompletableFuture;

import com.example.streamtest.CompletableFuture.FutureInterface.Shop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/27 19:12
 * @注释
 */
public class Test {


    //定制执行器


    static List<Shop> shops = Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"), new Shop("MyFavoriteShop"), new Shop("BuyItAll1"), new Shop("BuyItAll1"), new Shop("BuyItAll1"), new Shop("BuyItAll1"), new Shop("BuyItAll1"), new Shop("BuyItAll1"), new Shop("BuyItAll2"), new Shop("BuyItAll3"), new Shop("BestPrice"));

    private static final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),//避免线程数量太多超过负载，设置上线
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);//注意这里可能会出现递归调用new Thread 不是newThread
                    thread.setDaemon(true);//使用守护线程——这种方式不会阻止程序的关停
                    return thread;
                }
            });


    //采用查询所有商店的方式实现的findPrices方法
    public static List<String> findPrices(String product) {
        return shops.stream().map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))).collect(java.util.stream.Collectors.toList());
    }

    //采用并行流查询所有商店的方式实现的findPrices方法
    public static List<String> findPricesParallel(String product) {
        return shops.parallelStream().map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))).collect(java.util.stream.Collectors.toList());
    }


    //使用CompletableFuture实现findPrices方法
    public static List<String> finPricesAsync(String product) {
        List<CompletableFuture<String>> completableFutures = shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))).collect(Collectors.toList());
        //TODO 注意这里的分成两次流水线的区别
        //CompletableFuture join 和 Future get 具有相同含义一个一个的等待运行结束，得到结果
        //唯一区别是，join不会抛出任何异常
        return completableFutures.stream().map(CompletableFuture::join)//等待所有异步操作返回
                .collect(Collectors.toList());
    }


    //使用定制的执行器Executor
    private static List<String> finPricesAsyncExecutor(String product) {
        //TODO 注意这里的分成两次流水线的区别
        //CompletableFuture join 和 Future get 具有相同含义一个一个的等待运行结束，得到结果
        //唯一区别是，join不会抛出任何异常
        List<CompletableFuture<String>> completableFutures = shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor)).collect(Collectors.toList());


        return completableFutures.stream().map(CompletableFuture::join)//等待所有异步操作返回
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {

        //采用顺序查询所有商店的方式实现的findPrices方法
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        //Done in 4048 msecs 四秒钟多一点，顺序查询四个商店，每个商店耗时一秒钟


        //采用并行流查询所有商店的方式实现的findPrices方法
        start = System.nanoTime();
        System.out.println(findPricesParallel("myPhone27S"));
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        //Done in 1008 msecs 一秒钟多一点，顺序查询四个商店，每个商店耗时一秒钟


        start = System.nanoTime();
        System.out.println(finPricesAsync("myPhone27s"));
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        //Done in 1008 msecs 一秒钟多一点，使用异步的方式（电脑以足够的线程运行）


        //使用定制的执行器Executor
        //可以合理利用线程资源提升效率
        start = System.nanoTime();
        System.out.println(finPricesAsyncExecutor("myPhone27s"));
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

}