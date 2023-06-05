package com.example.streamtest.forkAndJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/29 19:21
 * @注释 RecursiveTask
 * 使用分支合并框架执行并行求和
 */
public class ForkJoinSumCalculator extends java.util.concurrent.RecursiveTask<Long> {//继承RecursiveTask来创建可以用于分支合并框架的任务
    /**
     * 要求求和的数组
     */
    private final long[] numbers;
    /**
     * 子任务处理的数组的起始位置
     */
    private final int start;
    /**
     * 子任务处理的数组的终止位置
     */
    private final int end;
    /**
     * 不再将任务分解为子任务的数组大小
     */
    public static final long THRESHOLD = 10_000;

    /**
     * 私有构造函数用于以递归的方式创建子任务
     */
    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    /**
     * 覆盖RecursiveTask抽象方法
     */
    @Override
    protected Long compute() {
        int length = end - start;
        //小于阈值，直接求和计算
        if (length <= THRESHOLD) {
            return this.computeSequentially();
        }
        //创建一个子任务来为数组的前一半求和
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        //利用用一个ForkJoinPool线程异步执行新创建的子任务
        leftTask.fork();
        //创建一个任务为数组的后一半求和
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        //同步执行第二个子任务有可能进一步递归划分
        Long rightResult = rightTask.compute();
        //读取一个子任务的结果，如果尚未完成就等待
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }


    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static void sum(Long n) {
        System.out.println("n = " + n);
        long[] longs = LongStream.rangeClosed(1, n).toArray();
        long start = System.currentTimeMillis();
        ForkJoinSumCalculator forkJoinSumCalculator = new ForkJoinSumCalculator(longs);
        Long invoke = new ForkJoinPool().invoke(forkJoinSumCalculator);
        System.out.println(System.currentTimeMillis() - start+" milliseconds");
        System.out.println(invoke);
    }

    public static void main(String[] args) {
        sum(10000000L);
    }
}
