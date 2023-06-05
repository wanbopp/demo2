package com.example.streamtest;

import com.example.streamtest.entity.Dish;
import com.example.streamtest.forkAndJoin.ForkJoinSumCalculator;

import javax.accessibility.AccessibleTableModelChange;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/5 19:19
 * @注释
 */
public class demo7 {
    /**
     * 并行流
     */


    //将顺序流转换为并行流
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    //顺序流
    public static long sequentialSUm(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }

    //传统迭代式求和
    public static long iterativeSum(long n) {
        long result = 0;
        for (int i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }


    //使用原始流进行归纳
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }


    //使用原始流进行归纳  并行版本
    public static long ParallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }


    //测量流的性能
    //测量对前N个自然数求和的函数的性能
    public static long measureSumPref(Function<Long, Long> addr, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = addr.apply(n);
            long duration = (System.nanoTime() - start) / 1000000;
            System.out.println("result = " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }


    //TODO 错误使用并行流---使用的算法改变了共享变量的状态
    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n)
                .parallel()
                .forEach(accumulator::add);
        return accumulator.getTotal();
    }


    public static void main(String[] args) {
//        long l = parallelSum(1000000);
//        System.out.println("l = " + l);
//        System.out.println("parallel sum done in:" + measureSumPref(demo7::parallelSum, 10000000));
//        l = 500000500000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        Squential sum done in:79
//        //使用传统迭代式
//        System.out.println("Iterative sum done in :" + measureSumPref(demo7::iterativeSum, 10000000) + " msecs");
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        result = 50000005000000
//        Iterative sum done in :3msecs

//        System.out.println("Squential sum done in: " + measureSumPref(demo7::sequentialSUm, 100000000) + " msecs");
//
//
//        //DOTO 补充 使用原始类型的流（特化）
//        System.out.println("ranged sum done in:" + measureSumPref(demo7::rangedSum, 100000000) + "msecs");
//
//        //特化流的并行版本
//        System.out.println("ranged sum done in:" + measureSumPref(demo7::ParallelRangedSum, 100000000) + "msecs");
//
//        //错误的使用并行流 改变共享变量
//        System.out.println("SideEffect parallel sum done in:" + measureSumPref(demo7::sideEffectSum, 100000000) + "msecs");


    sum(1000000L);

    }
    public static void sum(Long n) {
        System.out.println("n = " + n);
        long[] longs = LongStream.rangeClosed(1, n).toArray();
        ForkJoinSumCalculator forkJoinSumCalculator = new ForkJoinSumCalculator(longs);
        Long invoke = new ForkJoinPool().invoke(forkJoinSumCalculator);
        System.out.println(invoke);
    }
}
