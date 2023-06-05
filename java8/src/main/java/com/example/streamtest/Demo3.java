package com.example.streamtest;

import com.example.streamtest.entity.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/3/12 20:21
 * @注释
 */
public class Demo3 {
    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        test1(list);
//
//        ArrayList<Integer> integers = new ArrayList<>();
//        integers.add(1);
//        integers.add(2);
//        integers.add(3);
//        integers.add(4);
//        integers.add(5);
//        integers.add(5);
//        integers.add(5);
//        integers.add(6);
//        integers.add(7);
//        test2(integers);
//
//        List<Dish> dishes = Arrays.asList(
//                new Dish("pork", false, 800, Dish.Type.MEAT),
//                new Dish("beef", false, 700, Dish.Type.MEAT),
//                new Dish("chicken", false, 400, Dish.Type.MEAT),
//                new Dish("french fries", true, 530, Dish.Type.OTHER),
//                new Dish("rice", true, 550, Dish.Type.OTHER),
//                new Dish("season fruit", true, 120, Dish.Type.OTHER),
//                new Dish("pizza", false, 300, Dish.Type.FISH),
//                new Dish("prawns", false, 450, Dish.Type.FISH),
//                new Dish("salmon", false, 450, Dish.Type.FISH)
//        );
//        test3(dishes);
//        test4(dishes);
//        test5(dishes);
//        test7(dishes);


//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("a");
//        strings.add("ab");
//        strings.add("abc");
//        strings.add("abcd");
//        strings.add("abcde");
//        test6(strings);
//        test8(strings);


//        test9();
        test10();

    }

    public static void test1(List<String> list) {
        //使用distinct进行各异性 筛选 根据hash code 和 equals方法
        List<String> collect = list.stream().distinct().collect(Collectors.toList());
        collect.forEach(System.out::println);

    }

    public static void test2(List<Integer> list) {
        //使用filter筛选出所有的偶数
        //使用distinct进行各异性 筛选 根据hash code 和 equals方法
        List<Integer> collect = list.stream().filter(
                integer -> integer % 2 != 0
        ).distinct().collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    public static void test3(List<Dish> dishes) {
        List<Dish> collect = dishes.stream().sorted(new Comparator<Dish>() {
                    @Override
                    public int compare(Dish o1, Dish o2) {
                        return o2.getCalories() - o1.getCalories();
                    }
                }).limit(4)
                .collect(Collectors.toList());
        collect.forEach(dish -> System.out.println("dish.getName() = " + dish.getName() + ",dish.getCalories() = " + dish.getCalories()));
    }

    public static void test4(List<Dish> dishes) {
        List<Dish> collect = dishes.stream().sorted(new Comparator<Dish>() {
                    @Override
                    public int compare(Dish o1, Dish o2) {
                        return o1.getCalories() - o2.getCalories();
                    }
                }).filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .limit(4)
                .collect(Collectors.toList());
        collect.forEach(dish -> System.out.println("dish.getName = " + dish.getName() + ",dish.getCalories = " + dish.getCalories()));
    }

    public static void test5(List<Dish> dishes) {
        List<Dish> collect = dishes.stream().sorted(new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return o1.getCalories() - o2.getCalories();
            }
        }).filter(dish -> dish.getType() == Dish.Type.MEAT).limit(2).collect(Collectors.toList());
        collect.forEach(dish -> System.out.println("dish.getName = " + dish.getName() + ",dish.isVegetarian() = " + dish.isVegetarian()));
    }

    //使用map构造映射关系
    public static void test6(List<String> list) {
        List<Integer> collect = list.stream().map(String::length).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    //使用map构造 映射关系 计算每一道菜的名字
    public static void test7(List<Dish> list) {
        List<Integer> collect = list.stream().map(Dish::getName).map(String::length).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    //初始扁平化
    public static void test8(List<String> strings) {
        List<String[]> collect = strings.stream().map(s -> s.split("")).collect(Collectors.toList());
        collect.forEach(strings1 -> {
            for (String s : strings1) {
                System.out.println("strings1[i]" + s);
            }
        });
    }

    //使用Arrays.stream(T[] array)返回一个 泛型的 stream流
    public static void test9() {
        String[] strings = {"a", "abc", "abcd"};
        Stream<String> stream = Arrays.stream(strings);
        List<String> collect = stream.collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    public static void test10() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("ab");
        strings.add("abc");
        strings.add("abcd");
        strings.add("abcde");
        //显然不符合要求
        List<Stream<String>> collect = strings.stream().map(s -> s.split("")).map(Arrays::stream).collect(Collectors.toList());
        //使用flatMap将生成的每一个流扁平化一个流
        List<String> collect1 = strings.stream().map(s -> s.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        collect1.forEach(System.out::println);
    }
//规约 反复操作流，直到流被消耗完
    public static void tesst11(){
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
        Integer reduce = dishes.stream().map(Dish::getCalories).reduce(0, (a, b) -> a + b);
        Integer reduce1 = dishes.stream().map(Dish::getCalories).reduce(0, Integer::sum);//推荐操作

        Optional<Integer> reduce2 = dishes.stream().map(Dish::getCalories).reduce((a, b) -> a * b);//求积
        Optional<Integer> reduce3 = dishes.stream().map(Dish::getCalories).reduce(Integer::max);//最大值
        Optional<Integer> reduce4 = dishes.stream().map(Dish::getCalories).reduce(Integer::min);//最小值

    }


}
