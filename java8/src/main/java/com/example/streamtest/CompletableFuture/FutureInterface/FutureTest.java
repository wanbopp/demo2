package com.example.streamtest.CompletableFuture.FutureInterface;

import java.util.concurrent.*;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/20 8:38
 * @注释
 */
public class FutureTest {

    public double calculate(Double x, Double y) {
        ExecutorService pool = Executors.newCachedThreadPool();

        Future<Double> future = pool.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Thread.sleep(1001);//模拟耗时操作 超时后get方法会直接返回
                return x + y;
            }
        });

        //lambda表达式
        Future<Double> future1 = pool.submit(() -> x + y);

        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        } finally {
            pool.shutdown();
        }

    }

    public static void main(String[] args) {
        FutureTest futureTest = new FutureTest();
        double calculate = futureTest.calculate(1.0, 2.0);
        System.out.println("calculate = " + calculate);
    }
}
