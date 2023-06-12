package com.example.streamtest.refactor.RedesignMode.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 9:18
 * @注释
 */
public class Feed implements Subject{

    private final List<Observer> observers = new  ArrayList<Observer>();
    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void notifyObservers(String tweet) {
        observers.forEach(o -> o.notify(tweet));
    }


    public static void main(String[] args) {
        Feed feed = new Feed();
        //feed类中维护了观察者列表，通知时遍历列表，调用每个观察者的notify方法
        feed.registerObserver(new NYTimes());
        feed.registerObserver(new LeMonde());
        feed.registerObserver(new Guardian());
        feed.notifyObservers("The queen said her favourite book is Java 8 in Action!");


        // 1.0版本的缺点：每次添加新的观察者都需要修改Feed类，违反了开闭原则
        // 2.0版本的解决方案：使用Lambda表达式
        //消除僵化的代码，无需显示的实例化观察者，只需要传递一个Lambda表达式即可
        feed.registerObserver((String tweet) -> {
            if(tweet != null && tweet.contains("money")){
                System.out.println("Breaking news in NY! " + tweet);
            }
        });
    }
}
