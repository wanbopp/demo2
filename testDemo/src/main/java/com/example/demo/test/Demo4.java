package com.example.demo.test;

import com.example.demo.model.po.Employee;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/26 14:44
 * @注释
 */
public class Demo4 {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("name-li", "18", "1", 1),
                new Employee("name-wang", "19", "1", 2),
                new Employee("name-wang", "19", "2", 3),
                new Employee("name-zhao", "20", "1", 4),
                new Employee("name-pan", "21", "1", 5),
                new Employee("name-pan", "21", "2", 6),
                new Employee("name-wu", "22", "1", 7),
                new Employee("name-wu", "22", "2", 8),
                new Employee("name-zhang", "23", "1", 9)
        );
        //根据员工的姓名去重，保留num值最大的
        employees.stream().collect(Collectors.toMap(Employee::getName, Function.identity(), (oldValue, newValue) -> {
            if (oldValue.getNum() > newValue.getNum()) {
                return oldValue;
            } else {
                return newValue;
            }
        })).values().forEach(System.out::println);
        System.out.println("====================================");
        List<Employee> collect = employees.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Employee::getName))), ArrayList::new));
        collect.forEach(System.out::println);

    }
}
