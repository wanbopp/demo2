package com.example.streamtest;

import com.example.streamtest.Collectors.ToListCollectors;
import com.example.streamtest.entity.Dish;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/4/19 19:09
 * @注释
 */
public class demo5 {
    public static void main(String[] args) throws IOException {
        //由值创建流
        Stream<Integer> integerStream = Stream.of(1, 2, 2, 3, 3, 3, 3, 3, 33, 44);
//        integerStream.forEach(System.out::println);

        //由数组创建流
        int[] num = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        IntStream stream = Arrays.stream(num);
//        stream.forEach(System.out::println);


        //由文件生成流
//        Stream<String> lines = Files.lines(Paths.get("C:\\Users\\wanbo_pp\\Desktop\\新建 文本文档 (2).txt"), Charset.defaultCharset());
//        long count = lines.flatMap(line -> Arrays.stream(line.split(" ")))
//                .flatMap(s -> Arrays.stream(s.split("")))
//                .distinct()
//                .count();
//        System.out.println("count = " + count);


        //由函数生成无线流
        //Stream.iterate()  || Stream.generate
        Optional<Integer> reduce = Stream.iterate(1, item -> item * 2)
                .limit(20).reduce(Integer::sum);
//        reduce.ifPresent(System.out::println);
        //斐波那契数列
//        Stream<int[]> iterate = Stream.iterate(new int[]{0, 1}, ints -> new int[]{ints[1], ints[1] + ints[0]});
//        iterate.limit(20)
//                .map(ints -> ints[0])
//                .forEach(System.out::println);




        List<Dish> dishes = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 550, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", false, 300, Dish.Type.FISH),
                new Dish("prawns", false, 450, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        Optional<Dish> collect = dishes.stream().collect(Collectors.maxBy(new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return o1.getCalories() - o2.getCalories();
            }
        }));
//        System.out.println("collect.get = " + collect.get().getName());


        Optional<Dish> collect1 = dishes.stream().collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories)));
//        System.out.println("collect1.get() = " + collect1.get().getName());

        Optional<Dish> max = dishes.stream().max(Comparator.comparing(Dish::getCalories));
//        System.out.println("max.get().getName() = " + max.get().getName());


        //汇总
        //使用收集器
        Integer collect2 = dishes.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println("collect2 = " + collect2);
        //使用特化流+特化流的静态方法实现
        int sum = dishes.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("sum = " + sum);
        //使用summarizing一次性求最大值、最小值、平均值、数量统计等
        IntSummaryStatistics collect3 = dishes.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println("collect3.toString() = " + collect3.toString());
        //使用收集器 求平均值
        Double collect4 = dishes.stream().collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println("collect4 = " + collect4);
        //特化流+Stream的静态方法
        OptionalDouble average = dishes.stream().mapToInt(Dish::getCalories).average();
        System.out.println("average.getAsDouble() = " + average.getAsDouble());
        //使用收集器，分组求平均值
        Map<Dish.Type, IntSummaryStatistics> collect5 = dishes.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.summarizingInt(Dish::getCalories)));
        for (Map.Entry<Dish.Type, IntSummaryStatistics> entry : collect5.entrySet()) {
            Dish.Type key = entry.getKey();
            IntSummaryStatistics value = entry.getValue();
            System.out.println(key + " : " + value);
        }


        //链接字符串
        //使用joining工厂方法，返回的收集器
        String collect6 = dishes.stream().map(Dish::getName).collect(Collectors.joining());
        System.out.println("collect6 = " + collect6);
        String collect7 = dishes.stream().map(Dish::getName).collect(Collectors.joining(","));
        System.out.println("collect7 = " + collect7);
        //使用reduce
        String reduce1 = dishes.stream().map(Dish::getName).reduce("", (a, b) -> a + b);
        System.out.println("reduce1 = " + reduce1);
        //使用reducing
        String collect8 = dishes.stream().map(Dish::getName).collect(Collectors.reducing("", (a, b) -> a + b));
        String collect10 = dishes.stream().collect(Collectors.reducing("", Dish::getName, (a, b) -> a + b));
        System.out.println("collect8 = " + collect8);

        //广义的规约汇总
        //求出总和
        Integer collect9 = dishes.stream().collect(Collectors.reducing(
                0, Dish::getCalories, (a, b) -> a + b
        ));
        System.out.println("collect9 = " + collect9);
        //字符串拼接
        String collect11 = dishes.stream().collect(Collectors.reducing("", Dish::getName, (a, b) -> a + b));
        String collect12 = dishes.stream().collect(Collectors.reducing("", Dish::getName, String::concat));
        System.out.println("collect11 = " + collect11);
        System.out.println("collect12 = " + collect12);
        //找到最大值
        Optional<Dish> collect13 = dishes.stream().collect(Collectors.reducing((o1, o2) -> o1.getCalories() > o2.getCalories() ? o1 : o2));
        System.out.println("collect13 = " + collect13.get().getName());


        //收集和规约
        //steam的reduce和collect方法有何不同
        //使用reduce实现toListCollector
        Stream<Integer> stream1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).stream();//集合产生流
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);//由值产生流

        List<Integer> reduce2 = stream1.reduce(new ArrayList<Integer>(), (List<Integer> l, Integer e) -> {
                    l.add(e);
                    return l;
                },
                (List<Integer> ll, List<Integer> l2) -> {
                    ll.addAll(l2);
                    return ll;
                });


        //进行二级分组
        Map<Dish.Type, Map<String, List<Dish>>> collect14 = dishes.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy(dish -> {
            if (dish.getCalories() > 400) {
                return "HIGH";
            } else {
                return "low";
            }
        })));
        System.out.println("collect14.toString() = " + collect14.toString());

        //计算每一种类型的菜有多少个
        Map<Dish.Type, Long> collect15 = dishes.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        System.out.println("collect15.tp = " + collect15.toString());
        
        
        //计算每种类型的菜中 最大热量
        Map<Dish.Type, Optional<Dish>> collect16 = dishes.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.maxBy(Comparator.comparing(Dish::getCalories))));
        System.out.println("collect16.toString() = " + collect16.toString());
        Set<Map.Entry<Dish.Type, Optional<Dish>>> entries = collect16.entrySet();
        for (Map.Entry<Dish.Type, Optional<Dish>> entry : entries) {
            Dish.Type key = entry.getKey();
            System.out.println("key = " + key);
            int calories = entry.getValue().get().getCalories();
            System.out.println("calories = " + calories);
        }

        //分区
        //使用partitioningBy
        Map<Boolean, List<Dish>> collect17 = dishes.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        Set<Map.Entry<Boolean, List<Dish>>> entries1 = collect17.entrySet();
        for (Map.Entry<Boolean, List<Dish>> entry : entries1) {
            Boolean key = entry.getKey();
            System.out.println("key = " + key);
            entry.getValue().forEach(System.out::println);
        }

        //partitioningBy支持第二个参数 类型为收集器 可以进行进一步操作
        //区分素菜荤菜，再按类型进行分组
        Map<Boolean, Map<Dish.Type, List<Dish>>> collect18 = dishes.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
        

        //找到素材和荤菜中 热量最高的菜
        Map<Boolean, Dish> collect19 = dishes.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
        Set<Map.Entry<Boolean, Dish>> entries2 = collect19.entrySet();
        for (Map.Entry<Boolean, Dish> booleanDishEntry : entries2) {
            Boolean key = booleanDishEntry.getKey();
            System.out.println("key = " + key);
            Dish value = booleanDishEntry.getValue();
            System.out.println("value.toString() = " + value.toString());
        }

        //使用自定义的收集器
            Object collect20 = dishes.stream().filter(Dish::isVegetarian).collect(ToListCollectors.getToListCollectors());
            System.out.println("collect20 = " + collect20);
            List<Dish> collect21 = dishes.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
            System.out.println("collect21 = " + collect21);

    }
}
