package com.example.streamtest.refactor.strategy;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 16:12
 * @注释 责任链模式的抽象类
 */
public abstract class ProcessingObject <T>{
    //这种模式是通过定义一个戴白哦处理对象的抽象类来实现的，
    //在抽象了中会定义一个字段来记录后续的处理对象
    //一旦对象完成了他的工作，处理对象就会将他的工作转交给他的后续
    //处理对象，以此类推，直到最后一个处理对象
    protected ProcessingObject<T> successor;//接班人

    public void setSuccessor(ProcessingObject<T> successor){
        this.successor = successor;
    }

    public  T handle(T input){
        T r = handleWork(input);
        if(successor != null){
            return successor.handle(r);
        }
        return r;
    }

    abstract protected T handleWork(T input);//这有点像 模板方法 不同的策略有不同的实现






}
