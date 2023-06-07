package com.example.streamtest.refactor;

import com.example.streamtest.Car;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/7 17:23
 * @注释
 */
public class ToLambda {


    public static void doSomething(Runnable r) {
        r.run();
    }
    public static void doSomething(Car c) {
        c.run();
    }

    public static void main(String[] args) {

        //TODO 将单一抽象方法的匿名类转换为lambda表达式
        Runnable runnable = new Runnable() {//传统的方式使用匿名类
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };
        runnable.run();
        //使用lambda表达式
        Runnable runnable1 = () -> System.out.println("Hello");
        runnable1.run();


        //TODO lambda表达式不屏蔽包含类的变量
        int a = 10;
        Runnable runnable2 = () -> {
//            int a = 20; //编译错误 lambda表达式不屏蔽包含类变量
            System.out.println("Hello");
        };

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                int a = 20;  //编译通过
                System.out.println("Hello");
            }
        };


        //TODO lambda表达式在涉及重载的上下文中的类型推断 更加晦涩
        //使用匿名类，使用方法重载不会出现任何问题，初始化时确定
        doSomething(new Car() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        });

        //使用lambda表达式
//        doSomething(() -> System.out.println("Hello"));
        //编译错误，无法推断出lambda表达式的类型
        //显示类型转换来解决
        //doSomething((Car)() -> System.out.println("Hello"));


    }
}
