package wanbo.com.limiting;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/17 8:46
 * @注释 漏桶算法
 * <p>
 * 可以应对激增  但是速率固定
 */
@Slf4j
public class LeakBucketRateLimiter {
    // 桶的 容量
    private int capacity;

    //桶中现存水量
    private AtomicInteger water = new AtomicInteger(0);

    //开始漏水的时间
    private long leakTimeStamp;

    //水的流出速率，即每秒允许通过的请求数
    private int leakRate;

    public LeakBucketRateLimiter(int capacity, int leakRate) {
        this.leakRate = leakRate;
        this.capacity = capacity;
    }


    public synchronized boolean tryAcquire() {
        //桶中没有水，重新开始计算
        if (water.get() == 0) {
            log.info("start leaking");
            leakTimeStamp = System.currentTimeMillis();
            water.incrementAndGet();
            return water.get() < capacity;
        }

        //先漏水，计算剩余水量
        long currentTime = System.currentTimeMillis();
        int leakWater = (int) ((currentTime - leakTimeStamp) / 1000 * leakRate);
        log.info("lastTime:{}, currentTime:{}. LeakedWater:{}", leakTimeStamp, currentTime, leakWater);


        //可能时间不足则先不漏水
        if (leakWater != 0) {
            int leftWater = water.get() - leakWater;
            //可能水已经漏光，设为0
            water.set(Math.max(0, leftWater));
            leakTimeStamp = System.currentTimeMillis();
        }

        log.info("剩余容量：{}", capacity - water.get());

        if (water.get() < capacity) {
            log.info("tryAcquire success");
            water.incrementAndGet();
            return true;
        } else {
            log.info("tryAcquire fail");
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LeakBucketRateLimiter leakBucketRateLimiter = new LeakBucketRateLimiter(3, 2);
        for (int i = 0; i < 1000; i++) {
            if (leakBucketRateLimiter.tryAcquire()) {
                System.out.println("执行任务");
            } else {
                System.out.println("被限流");
            }
            TimeUnit.MILLISECONDS.sleep(300);
        }
    }
}
