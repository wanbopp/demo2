package com.example.demo.test;

import cn.hutool.core.lang.Assert;
import com.example.demo.model.po.Employee;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/3/17 9:09
 * @注释
 */
public class Demo2 {
    static final List<String> sequence = Arrays.asList(
            new String("分公司综合岗"),
            new String("分总"),
            new String("总公司管理员")
    );
//    public static void main(String[] args) {
//        Date date = new Date();
//        System.out.println("date.getTime() = " + date.getTime());
//
//        List<Employee> employees = Arrays.asList(
//                new Employee("name-li", "18", "1"),
//                new Employee("name-wang", "19", "1", 2),
//                new Employee("name-zhao", "20", "1", 1),
//                new Employee("name-pan", "21", "1", 1),
//                new Employee("name-wu", "22", "1", 2),
//                new Employee("name-zhang", "23", "1")
//        );
//        long count = employees.stream().filter(
//                employee -> employee.getNum() > 0
//        ).count();
//        System.out.println("count = " + count);
//
//        Map<Integer, List<Employee>> collect = employees.stream().collect(Collectors.groupingBy(Employee::getNum));
//        collect.forEach((integer, employees1) -> {
//            System.out.println("integer = " + integer);
//            employees1.forEach(System.out::println);
//        });
//
//
//        List<String> list2 = Arrays.asList("a", "b", "c");
//        List<String> list3 = Arrays.asList("d", "e", "f");
//        List<String> list4 = Arrays.asList("g", "h", "i");
//        List<String> list5 = Arrays.asList("j", "k", "l");
//        List<String> list6 = Arrays.asList("m", "n", "o");
//        List<String> list7 = Arrays.asList("p", "q", "r","s");
//        List<String> list8 = Arrays.asList("t", "u", "v");
//        List<String> list9 = Arrays.asList("w", "x", "y","z");
//        HashMap<Integer, List<String>> map = new HashMap<>();
//        map.put(2,list2);
//        map.put(3,list3);
//        map.put(4,list4);
//        map.put(5,list5);
//        map.put(6,list6);
//        map.put(7,list7);
//        map.put(8,list8);
//        map.put(9,list9);
//        int n = 2;
//        int m = 3;
//        List<String> collect1 = map.get(n).stream().flatMap(s -> map.get(m).stream().map(s1 -> new String(s + s1))).collect(Collectors.toList());
//        collect1.forEach(System.out::println);
//
//    }

    public void demo(int n,int m){
        List<String> list2 = Arrays.asList("a", "b", "c");
        List<String> list3 = Arrays.asList("d", "e", "f");
        List<String> list4 = Arrays.asList("g", "h", "i");
        List<String> list5 = Arrays.asList("j", "k", "l");
        List<String> list6 = Arrays.asList("m", "n", "o");
        List<String> list7 = Arrays.asList("p", "q", "r","s");
        List<String> list8 = Arrays.asList("t", "u", "v");
        List<String> list9 = Arrays.asList("w", "x", "y","z");
        HashMap<Integer, List<String>> map = new HashMap<>();
        map.put(2,list2);
        map.put(3,list3);
        map.put(4,list4);
        map.put(5,list5);
        map.put(6,list6);
        map.put(7,list7);
        map.put(8,list8);
        map.put(9,list9);
        List<String> collect1 = map.get(n).stream().flatMap(s -> map.get(m).stream().map(s1 -> new String(s + s1))).collect(Collectors.toList());
        collect1.forEach(System.out::println);

    }


    public void demo1(){

    }
    public static String getNextLevel() {
        int currentLevelIndex = sequence.indexOf(getRangeById());
        if (currentLevelIndex == -1) {
            // 如果等级字符串不在集合中,返回 null
            return null;
        } else if (currentLevelIndex == sequence.size() - 1) {
            //最高等级
            return sequence.get(currentLevelIndex);
        } else {
            // 返回下一个高等级字符串
            return sequence.get(currentLevelIndex + 1);
        }
    }

    public static String getRangeById() {
        List<String> list = Arrays.asList(
//                new String("分公司综合岗"),
//                new String("分总"),
                new String("总公司管理员"),
                new String("老板")
        );
        //筛选包含的字符串
        List<String> collect = list.stream().filter(sequence::contains).collect(Collectors.toList());
        Assert.notEmpty(collect,"当前用户没有权限");
        // 定义字符串优先级比较器
        Comparator<String> priorityComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                // 定义字符串优先级顺序
                int index1 = sequence.indexOf(s1);
                int index2 = sequence.indexOf(s2);
                return Integer.compare(index1, index2);
            }
        };
        return Collections.min(collect, priorityComparator);
    }

    public static void main(String[] args) {
        String rangeById = getRangeById();
        System.out.println("rangeById = " + rangeById);
        String nextLevel = getNextLevel();
        System.out.println("nextLevel = " + nextLevel);
    }
}
