package wanbo.com.limiting;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/18 8:35
 * @注释 guava 的令牌桶算法
 */
@Slf4j
public class GuavaRateLimiter {
    public static void main(String[] args) throws InterruptedException {
//        RateLimiter rateLimiter = RateLimiter.create(5);//限速器
//        Thread.sleep(2000);
//        for (int i = 0; i < 20; i++) {
//            double acquire = rateLimiter.acquire();
//            System.out.println("等待时间：" + acquire);
//        }
//
//        //预消费
//        //令牌桶算法通过预消费  解决流量突发的情况
//        //让后面的请求，等待一定时间，来支付前一请求的时间成本
//
//        RateLimiter rateLimiter = RateLimiter.create(1);
//        Thread.sleep(2000);
//        for (int i = 0; i < 3; i++) {
//            int num = 2 * i + 1;
//            System.out.println("获取的令牌个数num = " + num);
//            double cost = rateLimiter.acquire(num);
//            System.out.println("获取" + num + "个令牌结束，耗时" + cost + "毫秒");
//        }


        //平滑预热
        RateLimiter rateLimiter = RateLimiter.create(5, 3, TimeUnit.SECONDS);
        long startTimeStamp = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            double time = rateLimiter.acquire();
            System.out.println("等待时间:" + time + "s, 总时间:" + (System.currentTimeMillis() - startTimeStamp) + "ms");
        }
    }
}
