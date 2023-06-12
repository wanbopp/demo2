package com.example.streamtest.refactor.RedesignMode.observer;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 9:16
 * @注释 观察者模式的观察者 LeMonde
 */
public class LeMonde implements Observer{
    @Override
    public void notify(String tweet) {
        if(tweet != null && tweet.contains("wine")){
            System.out.println("Today cheese, wine and news! " + tweet);
        }
    }
}
