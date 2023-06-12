package com.example.streamtest.refactor.RedesignMode.ceLve;

import org.springframework.util.ObjectUtils;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/8 8:44
 * @注释  策略模式的上下文
 */
public class Validator {
    final private ValidationStrategy strategy;


    public Validator(ValidationStrategy strategy) {
        this.strategy = strategy;
    }


    public boolean validate(String s){
        return strategy.execute(s);
    }


    public static void main(String[] args) {
        //使用策略模式
        Validator validator = new Validator(new IsAllLowerCase());//传入一个检查是否全部小写的策略
        boolean a = validator.validate("Aaaa");
        System.out.println("Aaaa = " + a);
        boolean b = validator.validate("bbbb");
        System.out.println("bbbb = " + b);

        Validator validator1 = new Validator(new IsNumeric());//传入一个检查是否全部是数字的策略
        boolean c = validator1.validate("123");
        System.out.println("123 = " + c);

        Validator validator2 = new Validator(new IsPhone());//传入一个检查是否为合法手机号的策略
        boolean d = validator2.validate("12345678901");
        System.out.println("12345678901 = " + d);


        //使用lambda表达式 重构
        Validator validator3 = new Validator((String s) -> s.matches("[a-z]+"));
        boolean e = validator3.validate("aaaa");
        System.out.println("aaaa = " + e);

        Validator validator4 = new Validator((String s) -> s.matches("\\d+"));
        boolean f = validator4.validate("123");
        System.out.println("123 = " + f);

        //使用匿名类
        new Validator(new ValidationStrategy() {
            @Override
            public boolean execute(String s) {
                return false;
            }
        });


        String s = "";
        String s1 = " ";
        String s2 = new String();
        System.out.println("s = " + s);
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);

        System.out.println("ObjectUtils.isEmpty(s) = " + ObjectUtils.isEmpty(s));
        System.out.println("ObjectUtils.isEmpty(s1) = " + ObjectUtils.isEmpty(s1));
        System.out.println("ObjectUtils.isEmpty(s2) = " + ObjectUtils.isEmpty(s2));


    }
}
