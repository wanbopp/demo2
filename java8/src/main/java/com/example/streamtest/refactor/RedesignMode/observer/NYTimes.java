package com.example.streamtest.refactor.RedesignMode.observer;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 9:12
 * @注释  观察者模式的观察者 NYTimes
 */
public class NYTimes implements Observer{
    @Override
    public void notify(String tweet) {
        if(tweet != null && tweet.contains("money")){
            System.out.println("Breaking news in NY! " + tweet);
        }
    }
}
