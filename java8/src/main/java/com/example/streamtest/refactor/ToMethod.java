package com.example.streamtest.refactor;

import com.example.streamtest.entity.CaloricLevel;
import com.example.streamtest.entity.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Collectors.*;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/7 17:59
 * @注释 lambda -> 方法引用
 */
public class ToMethod {

   static List<Dish> menu = Arrays.asList(
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

    public static void main(String[] args) {
        //lambda 表达式
        Map<String, List<Dish>> collect = menu.stream().collect(Collectors.groupingBy(dish -> {
            if (dish.getCalories() <= 400) {
                return "低热量";
            } else if (dish.getCalories() <= 700) {
                return "中等热量";
            } else {
                return "高热量";
            }
        }));
        collect.forEach((k,v)->{
            System.out.println("k = " + k);
            v.forEach(System.out::println);
        });
        //方法引用
        Map<CaloricLevel, List<Dish>> collect1 = menu.stream().collect(Collectors.groupingBy(Dish::getCaloricLevel));
        collect1.forEach((k,v)->{
            System.out.println("k = " + k);
            v.forEach(System.out::println);
        });

    }


}
