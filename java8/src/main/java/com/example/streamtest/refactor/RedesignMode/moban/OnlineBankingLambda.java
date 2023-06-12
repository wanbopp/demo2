package com.example.streamtest.refactor.RedesignMode.moban;

import com.example.streamtest.refactor.modle.Customer;
import com.example.streamtest.refactor.modle.Database;

import java.util.function.Consumer;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 8:52
 * @注释
 */
public class OnlineBankingLambda {

    //使用lambda进行重构
    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }
}
