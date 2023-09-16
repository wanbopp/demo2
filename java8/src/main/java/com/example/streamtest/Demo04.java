package com.example.streamtest;

import com.example.streamtest.entity.Trader;
import com.example.streamtest.entity.Transaction;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/3/21 14:50
 * @注释
 */
public class Demo04 {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brain = new Trader("Brain", "Cambridge");


        List<Transaction> transactions = Arrays.asList(
                new Transaction(brain, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        //TODO 找出2011年 发生的所有交易，并按交易额进行低到高的排序
        //写法 F1
        List<Transaction> collect = transactions.stream().filter(transaction -> transaction.getYear() == 2011).sorted(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getYear() - o2.getYear();
            }
        }).collect(Collectors.toList());
        //写法F2
        List<Transaction> collect1 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))//方法引用
                .collect(Collectors.toList());
//        collect1.forEach(System.out::println);



        //TODO 交易员在那些城市工作过
        //使用distinct
        List<String> collect2 = transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
//        collect2.forEach(System.out::println);
        //使用set集合
        Set<String> collect3 = transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .collect(Collectors.toSet());
//        collect3.forEach(System.out::println);
        //查找所有来自于剑桥的交易员，并按姓名排序
        List<Transaction> collect4 = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("2"))
                .sorted(comparing(transaction -> transaction.getTrader().getName()))
                .distinct()
                .collect(Collectors.toList());
//        collect4.forEach(System.out::println);
//        Assert.notEmpty(collect4,"车辆不存在");


        //TODO 查找所有来自于剑桥的交易员，并按姓名排序。
        List<Trader> cambridge = transactions.stream().map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(Collectors.toList());
//        cambridge.forEach(System.out::println);

        //TODO 返回所有交易员的姓名字符串，按字母顺序排序
        String reduce = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
//        System.out.println("reduce = " + reduce);


        //TODO 有没有交易员实在米兰工作的
        boolean milan = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
//        System.out.println("milan = " + milan);


        //TODO 打印所有生活在剑桥的交易员的所有交易金额
//        transactions.stream()
//                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
//                .map(Transaction::getValue)
//                .forEach(System.out::println);




        //TODO 所有交易中，最高的交易额是多少
        Optional<Integer> reduce1 = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
//        System.out.println("reduce1.get() = " + reduce1.get());
   
        //TODO 找到交易额最小的交易
        Optional<Transaction> reduce2 = transactions.stream()
                .reduce((o1, o2) -> o1.getValue() > o2.getValue() ? o2 : o1);
        System.out.println("reduce2.get() = " + reduce2.get());


    }
    

}
