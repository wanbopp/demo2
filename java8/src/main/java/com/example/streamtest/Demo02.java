package com.example.streamtest;

import com.example.streamtest.entity.Dish;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/3/10 10:58
 * @注释
 */
public class Demo02 {
    public static void main(String[] args) {
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

        List<String> collect = dishes.stream().filter(item -> item.getCalories() > 300).limit(3).map(Dish::getName).collect(Collectors.toList());
        collect.forEach(System.out::println);
        System.out.println("使用for-each循环，外部迭代=====================");
        List<String> names = new ArrayList<>();
        //使用for-each循环，外部迭代
        for (Dish dish:dishes){
            names.add(dish.getName());
        }
        names.forEach(System.out::println);
        System.out.println("使用迭代器外部循环=====================");
        //使用迭代器外部循环
        Iterator<Dish> iterator = dishes.iterator();
        while(iterator.hasNext()){
            names.add(iterator.next().getName());
        }
        names.forEach(System.out::println);
        System.out.println("使用流内部迭代=====================");
        List<String> collect1 = dishes.stream().map(Dish::getName).collect(Collectors.toList());
        collect1.forEach(System.out::println);

        System.out.println("distinct进行去重，返回个数=====================");
        long count = dishes.stream().filter(dish -> {
            System.out.println("dish.getName() = " + dish.getName());
            return dish.getCalories() > 400;})
                .distinct()
                .limit(4)
                .count();
        System.out.println("count = " + count);

        System.out.println("使用distinct对对象集合的某一属性去重=====================");
        ArrayList<Dish> collect2 = dishes.stream().collect(collectingAndThen(
                toCollection(() -> new TreeSet<>(Comparator.comparing(Dish::getCalories))), ArrayList::new));
        collect2.forEach(dish -> System.out.println("dish.getCalories() = " + dish.getCalories()));
        System.out.println("使用filter对对象集合的某一属性去重=====================");
        List<Dish> collect3 = dishes.stream().filter(dish -> {
            HashSet<Object> set = new HashSet<>();
            return set.add(dish.getCalories());
        }).collect(Collectors.toList());
        collect3.forEach(dish -> System.out.println("dish.getCalories() = " + dish.getCalories()));

        System.out.println("使用并行流，利用多核系统的优势=====================");
        List<String> collect4 = dishes.parallelStream().map(Dish::getName).collect(Collectors.toList());
        collect4.forEach(System.out::println);

        System.out.println("使用使用谓词进行筛选  判断是不是荤菜=====================");
        //针对Boolean值
        List<Boolean> collect5 = dishes.stream().map(Dish::isVegetarian).filter(vegetarian -> {
            return !vegetarian;
        }).collect(Collectors.toList());
        collect5.forEach(System.out::println);
        List<Boolean> collect6 = dishes.stream().filter(Dish::isVegetarian).map(Dish::isVegetarian).collect(Collectors.toList());
        collect6.forEach(System.out::println);

    }
}
