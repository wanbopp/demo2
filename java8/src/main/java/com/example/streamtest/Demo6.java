package com.example.streamtest;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/4/27 16:28
 * @注释
 */
@Validated
public class Demo6 {

    //是不是质数
    public boolean isPrime(@Valid int candidate){
        return IntStream.range(2,candidate)
                .noneMatch(i -> candidate % i == 0);
    }


    //优化  仅仅检测待测平方根 减少比较次数
    public static Boolean isPrime2(int candidate){
        return IntStream.range(2,(int)Math.sqrt((double)candidate))
                .noneMatch(i -> candidate % i ==0);
    }


    //把前N个数分为质数和非质数
    public Map<Boolean, List<Integer>> partitionPrimes(int n){
        return IntStream.range(2,n).boxed()
                .collect(Collectors.partitioningBy(this::isPrime));
    }

    public static void main(String[] args) {
        Demo6 demo6 = new Demo6();
        Boolean prime2 = isPrime2(17);
        System.out.println("prime2 = " + prime2);
        boolean prime = demo6.isPrime(17);
        System.out.println("prime = " + prime);

        //分区区分质数、非质数
        for (Map.Entry<Boolean, List<Integer>> booleanListEntry : demo6.partitionPrimes(1000).entrySet()) {
            System.out.println("booleanListEntry.getKey() = " + booleanListEntry.getKey());
            System.out.println("booleanListEntry.getKey() = " + booleanListEntry.getValue().toString());
        }

    }











}
