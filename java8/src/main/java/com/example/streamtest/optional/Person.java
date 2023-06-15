package com.example.streamtest.optional;

import java.util.Optional;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/13 19:16
 * @注释 Optional类是一个可以包含或者不包含非空值的容器类
 */
public class Person {

    private Optional<Car> car;
    public Optional<Car> getCar() { return car; }
    //人可能有车 也可能没车 所以实现Optional封装
    //车可能有保险，也可能没有保险所以使用Optional封装
    //保险公司必须有名字，所以不用Optional封装  如果最后发现保险公司名字是空、或者出现了空指针异常，那么就是数据出错了
    //在代码中始终如一的使用Optional可以很好的界定出变量值的缺失是结构的问题，还是算法的缺陷，还是数据中的问题


    public static void main(String[] args) {
        Optional<Object> empty = Optional.empty();//创建一个空的Optional对象

        //依据非空值创建Optional
        Optional<String> hello = Optional.of("hello");

        //可接受null的Optional
        Optional<Object> o = Optional.ofNullable(null);
        hello.get();//如果Optional中有值则返回该值，否则抛出NoSuchElementException异常


        //flatMap
        Person person = new Person();
//        Optional.of(person)
//                .map(Person::getCar)//返回Optional<Optional<Car>>
//                .map(Car::getInsurance)
//                .map(Insurance::getName);
        //错误因为map中Person::getCar返回的是 Optional<Car> 而不是Car 后续无法调用

        //采用类似于流的中flatMap 将流中元素扁平化的思想
        //Optional.flatMap()方法将 Optional<Optional<Car>> 转换为一层Optional<Car>

        //使用Optional获取car的保险公司的名称
        Optional.of(person)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");//如果Optional的结果值为空，设置默认值

        //注意Optional 未实现序列化
    }



}
