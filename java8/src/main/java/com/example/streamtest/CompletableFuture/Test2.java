package com.example.streamtest.CompletableFuture;

import com.example.streamtest.CompletableFuture.FutureInterface.Shop;

import java.util.Arrays;
import java.util.List;
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

    public static void main(String[] args) {
        //以最简单的方式实现使用Discount服务的findPrices方法
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");

        //使用并行流非常容易提升性能（简单场景下可以用）
        //但是因为Stream底层依赖的是线程数量固定的通用线程池，如果商店数量增加，扩展性不好
        //相反，你也知道CompletableFuture,可以自定义调度任务执行的执行器能够充分利用CPU资源

        //

    }
}
