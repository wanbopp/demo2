package com.example.streamtest.refactor.RedesignMode.moban;

import com.example.streamtest.refactor.modle.Customer;
import com.example.streamtest.refactor.modle.Database;

import java.util.function.Consumer;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 8:29
 * @注释 模板方法模式
 */
abstract class OnlineBanking {
    public void processCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy(c);
    }

    //不用的支行，实现不同的makeCustomerHappy方法，实现差异化
    abstract void makeCustomerHappy(Customer c);


    public static void main(String[] args) {
        //模板方法需要针对不同的策略，建立不同的子类，实现不用的策略
        //。。。。。


        //使用lambda进行重构，方法中增加参数，传入不同的策略 无需在新建子类
        new OnlineBankingLambda() {
        }.processCustomer(1337, (Customer c) -> System.out.println("重构后的策略2"));

        new OnlineBankingLambda() {
        }.processCustomer(1337, (Customer c) -> System.out.println("重构后的策略3"));
        //lambda 表达式解决了设计模式与生俱来的设计僵化问题

    }
}
