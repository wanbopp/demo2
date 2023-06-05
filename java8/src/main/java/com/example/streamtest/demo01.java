package com.example.streamtest;

import com.example.streamtest.entity.Employee;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/3/9 15:06
 * @注释
 */
public class demo01 {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Employee> employees = new ArrayList<>();
        Employee employee4 = new Employee();
        employee4.setName("李四");
        employee4.setBirthDate(new Date());
        employee4.setAge(4);
        Thread.sleep(1000);
        Employee employee1 = new Employee();
        employee1.setName("张三");
        employee1.setBirthDate(new Date());
        employee1.setAge(1);
        Employee employee3 = new Employee();
        employee3.setName("张三");
        employee3.setBirthDate(new Date());
        employee3.setAge(3);
        Employee employee2 = new Employee();
        employee2.setName("张三");
        Thread.sleep(1000);
        employee2.setBirthDate(new Date());
        employee2.setAge(2);
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);






        employees.stream().map(employee -> {employee.setGender(employee.getName());return  employee;}).collect(Collectors.toList()).forEach(System.out::println);}
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
//        //sort 需要传入比较器对象
//        employees.sort(new Comparator<Employee>() {
//            @Override
//            public int compare(Employee o1, Employee o2) {
//                return Integer.compare(o1.getAge(), o2.getAge());
//            }
//        });
////        employees.forEach(System.out::println);//直接打印
//////        集合的sort方法
////        Employee employee5 = new Employee();
////        employee2.setName("王五");
////        employee2.setBirthDate(new Date());
////        employee2.setAge(5);
////        employees.add(employee5);
////        employees.sort(new Comparator<Employee>() {
////            @Override
////            public int compare(Employee o1, Employee o2) {
////                return o1.getAge()-o2.getAge();
////            }
////        });
////        //对日期进行排序--集合的sort方法
////        employees.sort(new Comparator<Employee>() {
////            @Override
////            public int compare(Employee o1, Employee o2) {
////                return o1.getBirthDate().compareTo(o2.getBirthDate());
////            }
////        });
////        //利用Stream流 日期传唤为时间戳
////        List<Employee> collect1 = employees.stream().sorted(((o1, o2) ->
////                Long.compare(o1.getBirthDate().getTime(), o2.getBirthDate().getTime())
////        )).collect(Collectors.toList());
////        employees.forEach(System.out::println);
////
////
////        employees.sort(Comparator.comparing(Employee::getBirthDate, (Date::compareTo)));
////        employees.forEach(System.out::println);//直接打印
//
//        employees.stream().map(Employee::getName).collect(Collectors.toList()).forEach(System.out::println);
//    }
//
//    public static Map<String,String>  map;
//
//    public static void  mapToString(){
//        map = new HashMap<>();
//        map.put("1","a");
//        map.put("2","b");
//        map.put("3","c");
//        map.put("4","d");
//        System.out.println("map = " + map.toString());
//    }
//    public static void  mapToJson(){
//
//
//    }
//
//
//    public static void main(String[] args) {
//        mapToString();
//
//    }
}