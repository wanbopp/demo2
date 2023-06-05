package wanbo.com.limiting;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/12 17:50
 * @注释
 */
public class FixedWindowRateLimiter {

    //时间窗口的大小
    private Long windowSize;


    //允许通过请求数
    private int maxRequestCount;


    //当前窗口通过的请求计数
    private AtomicInteger count = new AtomicInteger(0);


    //窗口的右边界
    private long windowBorder;

    //构造函数
    public FixedWindowRateLimiter(Long windowSize, int maxRequestCount) {
        this.windowSize = windowSize;
        this.maxRequestCount = maxRequestCount;
        windowBorder = System.currentTimeMillis() + windowSize;
    }


    public synchronized boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        //固定窗口的时间已经到了 就再次初始化
        if (windowBorder < currentTime) {
            System.out.println("window reset");
            do {
                windowBorder += windowSize;
            } while (windowBorder < currentTime);

            count = new AtomicInteger(0);
        }

        if (count.intValue() < maxRequestCount) {
            count.incrementAndGet();//+1
            System.out.println("tryAcquire success");
            return true;
        } else {
            System.out.println("tryAcquire fail");
            return false;
        }


    }

    public static void main(String[] args) throws InterruptedException {

        FixedWindowRateLimiter fixedWindowRateLimiter
                = new FixedWindowRateLimiter(10000L, 5);

        for (int i = 0; i < 10000; i++) {
            if (fixedWindowRateLimiter.tryAcquire()) {
                System.out.println("执行任务");
            } else {
                System.out.println("被限流");
                TimeUnit.MILLISECONDS.sleep(300);
            }
        }

    }
}