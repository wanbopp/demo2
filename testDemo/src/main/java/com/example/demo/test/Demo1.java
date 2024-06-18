package com.example.demo.test;

import com.example.demo.model.po.Dish;
import com.example.demo.model.po.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/3/13 14:56
 * @注释
 */
public class Demo1 {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("name-li", "18", "1"),
                new Employee("name-wang", "19", "1"),
                new Employee("name-zhao", "20", "1"),
                new Employee("name-pan", "21", "1"),
                new Employee("name-wu", "22", "1"),
                new Employee("name-zhang", "23", "1")
        );
//        employees.forEach(employee -> {
//            String name = employee.getName();
//            employee.setName(name.substring(name.indexOf("name-")));
//        })

        Function<Employee,Employee> changeClientName = employee -> {
            String name = employee.getName();
            int index = name.lastIndexOf("name-");
            System.out.println("index = " + index);
            String substring = name.substring(5);
            System.out.println("substring = " + substring);
            employee.setName(substring);
            employee.setGender(name);
            return employee;
        };
        List<Employee> collect = employees.stream().map(changeClientName).collect(Collectors.toList());

        collect.forEach(employee -> System.out.println("employee.getName() = " + employee.getName()));
    }

}
