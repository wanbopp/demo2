package com.example.streamtest.refactor.RedesignMode.observer;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 9:17
 * @注释  观察者模式的主题接口
 */
public interface Subject {
    //注册观察者
    void registerObserver(Observer o);
    //通知观察者
    void notifyObservers(String tweet);
}
