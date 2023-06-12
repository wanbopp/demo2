package com.example.streamtest.refactor.RedesignMode.observer;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 9:13
 * @注释 观察者模式的观察者 Guardian
 */
public class Guardian implements Observer{

    @Override
    public void notify(String tweet) {
        if(tweet != null && tweet.contains("queen")){
            System.out.println("Yet another news in London... " + tweet);
        }
    }
}
