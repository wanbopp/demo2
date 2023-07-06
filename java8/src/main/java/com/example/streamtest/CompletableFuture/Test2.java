package com.example.streamtest.CompletableFuture;

import com.example.streamtest.CompletableFuture.FutureInterface.Shop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/28 19:47
 * @注释 多个异步任务进行流水线操作
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
    public static List<String> findPricesAsync(String Product) {
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


    //使用thenCombine方法实现两个异步任务的流水线
    public static void thenCombineTest(String product) {
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> Shop.getPrice(product), executor)
                .thenCombine(CompletableFuture.supplyAsync(
                                () -> Exchange.getRate(Money.EUR, Money.USD)),//第二个参数类型为BiFunction
                        (price, rate) -> price * rate);

        System.out.println(future.join());
    }

    //最终优化
    //1、每一个商店价格查询器增加了随机的延迟
    //2、使用thenApply不会阻塞你的代码，等待上一步操作的结果，进行处理
    //3、使用thenCombine等待前两个异步操作结束后 进行操作
    //4、thenCompose 将两个CompletableFuture对象串联起来，
    //接收前一个CompletableFuture对象返回的结果作为参数，返回另外一个CompletableFuture
    public static Stream<CompletableFuture<String>> findPriceStream(String product) {
        return shops.stream()
                .map((shop) -> CompletableFuture.supplyAsync(() -> shop.getPriceStrWithRandom(product), executor))//向各个商店查询价格 返回Stream<CompletableFuture<String>>
                .map(future -> future.thenApply(Quote::parse))//无需等待上一步异步任务结束，直接解析为Quote对象,流水线执行就行，使用Future的thenApply方法进行下一步操作，返回Stream<CompletableFuture<Quote>>
                .map(quoteCompletableFuture -> quoteCompletableFuture.thenCompose(quote ->
                        CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));//在当前线程中进行进行合并操作，不使用另外的线程
    }


    public static void main(String[] args) {
        //以最简单的方式实现使用Discount服务的findPrices方法
//        long start = System.nanoTime();
//        System.out.println(findPrices("myPhone27S"));
//        long duration = (System.nanoTime() - start) / 1_000_000;
//        System.out.println("Done in " + duration + " msecs");


//        thenCombineTest("myPhone27S");


        //价格查询器的最终优化
        //Stream流中必须有终端操作，才会触发流水线的执行
        long start = System.nanoTime();
        CompletableFuture[] futures = findPriceStream("myPhone27S")
                .map(f -> f.thenAccept(//在流水线执行完成后 进行操作
                        s -> System.out.println(s + " (done in " +
                                ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();//allOf工厂方法接收一个由CompletableFuture构成的数组，数组中的所有CompletableFuture对象执行完成之后，它返回一个CompletableFuture<Void>对象。这意味着，如果你需
        //要等待最初Stream中的所有 CompletableFuture对象执行完毕，对 allOf方法返回的CompletableFuture执行join操作是个不错的主意。


        System.out.println("All shops have now responded in "
                + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

}
