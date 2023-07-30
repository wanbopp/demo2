package wanbo.com.limiting;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/15 8:35
 * @注释 滑动窗口算法
 * 滑动窗口将时间窗口进行了更精细的分片，将固定窗口分为多个小块，每次只滑动一小块时间
 * 并且每个时间段内都维护了 单独的计数器 每次滑动都减去前一段时间块内的请求数，
 * 每一个时间块超过阈值也会限流
 */
@Slf4j
public class SlidingWindowRateLimiter {
    //时间窗口大小 单位毫秒
    private long windowSize;

    //分片窗口数
    private int shardNum;

    //允许通过请求数
    private int maxRequestCount;

    // 各个窗口内 请求计数
    private int[] shardRequestCount;

    // 请求总数
    private int totalCount;

    //当前窗口下标
    private int shardId;

    //每个窗口大小 单位毫秒
    private long tinyWindowSize;

    //窗口右边界
    private long windowBorder;


    public SlidingWindowRateLimiter(long windowSize, int shardNum, int maxRequestCount) {
        this.windowSize = windowSize;
        this.shardNum = shardNum;
        this.maxRequestCount = maxRequestCount;
        shardRequestCount = new int[shardNum];
        tinyWindowSize = windowSize / shardNum;
        windowBorder = System.currentTimeMillis();//TODO 是否加上窗口大小
    }

    public synchronized boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > windowBorder) { //时间超过窗口右边界  循环创建窗口
            do {
                shardId = (++shardId) % shardNum;
                totalCount -= shardRequestCount[shardId];
                shardRequestCount[shardId] = 0;
                windowBorder += tinyWindowSize;
            } while (windowBorder < currentTime);
        }


        if (totalCount < maxRequestCount) {
            log.info("tryAcquire success,{}", shardId);
            shardRequestCount[shardId]++;
            totalCount++;
            return true;
        } else {
            log.info("tryAcquire fail,{}", shardId);
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SlidingWindowRateLimiter slidingWindowRateLimiter = new SlidingWindowRateLimiter(1000, 10, 10);
        for (int i = 0; i < 100; i++) {
            boolean b = slidingWindowRateLimiter.tryAcquire();
            if (b) {
                System.out.println("执行任务");
                System.out.println(slidingWindowRateLimiter.shardId);
            } else {
                System.out.println("被限流");
            }
            TimeUnit.MILLISECONDS.sleep(15);
        }


    }

    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode head = null;
            ListNode tail = null;
            int carry = 0;
            while (l1 != null || l2 != null){

            }






            return head;

        }
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


}
