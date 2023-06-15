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

    private int age;

    public void setCar(Optional<Car> car) {
        this.car = car;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Optional<Car> getCar() {
        return car;
    }
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


//**************************************使用filter剔除特定值**************************
        //传统的方法，先判断对象是否存在，在对属性值进行判断
        Insurance insurance = new Insurance();
        if (insurance != null && "平安保险".equals(insurance.getName())) {
            System.out.println("ok");
        }
        //是用Optional的filter方法对代码进行重构
        Optional<Insurance> optionalInsurance = Optional.of(insurance);
        Optional<Insurance> optionalInsurance1 = optionalInsurance.filter(insurance1 -> "平安保险".equals(insurance1.getName()));
        optionalInsurance1.ifPresent(x -> System.out.println("ok"));
        //filter方法接受一个谓词参数，如果Optional对象的值存在，并且它符合谓词的条件，filter方法就返回其值，
        //否则就返回一个空的Optional对象

    }


    //两个Optional对象的组合
    //接受Optional<Car>、Optional<Person> 作为参数返回 最便宜的保险公司
    public Optional<Insurance> getCheapestInsurance(Optional<Person> person, Optional<Car> car) {
        //如果person为null 则返回空的Optional对象
        if (person.isPresent() && car.isPresent()) {
            return Optional.of(findCheapestInsurance(person.get(), car.get()));
        } else {
            return Optional.empty();
        }

    }


    //这样看起来跟传统的null检查没有什么区别
    //结合map和flatMap方法重写
    public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
        //学习这个写法
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
        //对第一个Optional调用flatMap方法，如果他是空值，则传递给它的lambda表达式不会执行，直接返回一个空的Optional
        //car的map操作也是如此
    }


    private Insurance findCheapestInsurance(Person person, Car car) {
        //查询保险公司
        //比较所有数据
        Insurance insurance = new Insurance();
        insurance.setName("平安保险");
        return insurance;
    }


    //测试 对Option对象进行过滤
    //找出年龄大于或者等于minAge参数的Person所对应的保险公司列表。
    String getCarInsuranceName(Optional<Person> person, int minAge) {
        return person.filter(a -> a.getAge() > minAge)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }





    //异常与Optional
    //Integer.parseInt(String) 封装到工具类中

    public static Optional<Integer> stringToInt(String s){
        try {
            return Optional.of(Integer.parseInt(s));
        }catch(NumberFormatException e){
            return Optional.empty();
        }
    }







}
