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
 * @Date 2023/6/28 19:47
 * @注释  多个异步任务进行流水线操作
 */
public class Test2 {
    //初始化商店
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
    public static List<String> findPrices(String product) {
        //三次map操作
        //第一次map操作：查询商店
        //第二次map操作：将查询价格服务返回的字符串解析为Quote对象
        //第三次map操作：联系Discount服务，根据折扣代码插叙折扣后的价格
        return shops.stream()
                .map(shop -> Shop.getPriceWithDiscount(product))//取得每个shop对象中商品的原始价格
                .map(Quote::parse)//解析报价字符串
                .map(Discount::applyDiscount)//联系Discount服务，为每个Quote申请折扣
                .collect(Collectors.toList());
    }

    //使用Completable实现findPrices方法
    public static List<String> findPricesAsync(String Product){
        List<CompletableFuture<String>> collect = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceStr2(Product), executor))
                //以异步的方式取得每个shop中指定产品的原始价格、折扣编码
                .map(future -> future.thenApply(Quote::parse))
                //将价格服务返回的字符串转换为Quote对象
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
                //调用折扣服务返回 包含真实价格的 Future
                .collect(Collectors.toList());


        return collect.stream()
                .map(CompletableFuture::join)
                //等待流中的所有Future执行完毕，并提取各自的返回值
                .collect(Collectors.toList());
    }


    //使用并行流
    public static List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> Shop.getPriceWithDiscount(product))//取得每个shop对象中商品的原始价格
                .map(Quote::parse)//解析报价字符串
                .map(Discount::applyDiscount)//联系Discount服务，为每个Quote申请折扣
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        //以最简单的方式实现使用Discount服务的findPrices方法
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");


        //使用并行流非常容易提升性能（简单场景下可以用）
        //但是因为Stream底层依赖的是线程数量固定的通用线程池，如果商店数量增加，扩展性不好
        //相反，你也知道CompletableFuture,可以自定义调度任务执行的执行器能够充分利用CPU资源

        //使用Completable构建异步操作
        start = System.nanoTime();
        System.out.println(findPricesAsync("myPhone27S"));
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");


        //使用并行流
        start = System.nanoTime();
        System.out.println(findPricesParallel("myPhone27S"));
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");

    }
}
