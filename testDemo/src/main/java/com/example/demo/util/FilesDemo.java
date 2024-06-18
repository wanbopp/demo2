//package com.example.demo.util;
//
//import com.example.demo.model.po.Employee;
//import com.example.demo.model.vo.EmployeeVo;
//import org.springframework.beans.BeanUtils;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * 一个用于Files类练习的Demo
// * @version 1.0
// * @Author wanbo_pan
// * @Date 2023/3/6 13:34
// * @注释
// */
//public class FilesDemo {
//
//    public static void main(String[] args) {
//        ArrayList<Employee> employees = new ArrayList<>();
//        Employee employee1 = new Employee();
//        employee1.setName("张三");
//        employee1.setBirthDate(new Date());
//        Employee employee2 = new Employee();
//        employee2.setName("张三");
//        employee2.setBirthDate(new Date());
//        Employee employee3 = new Employee();
//        employee3.setName("张三");
//        employee3.setBirthDate(new Date());
//        Employee employee4 = new Employee();
//        employee4.setName("李四");
//        employee4.setBirthDate(new Date());
//        employees.add(employee1);
//        employees.add(employee2);
//        employees.add(employee3);
//        employees.add(employee4);
////        List<EmployeeVo> employeeVos = employees.stream().map(employee -> {
////            EmployeeVo employeeVo = new EmployeeVo();
////            BeanUtils.copyProperties(employee, employeeVo);
////            return employeeVo;
////        }).collect(Collectors.toList());
//        employees.forEach(System.out::println);//直接打印
//        List<Employee> collect = employees.stream().filter(employee ->
//                !Objects.equals(employee.getName(), "李四")
//        ).collect(Collectors.toList());
//        for (Employee employee : collect) {
//            System.out.println("employeeIterator.next().getName() = " + employee.getName());
//
//        }
//    }
//}
