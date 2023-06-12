package com.example.streamtest.refactor.RedesignMode.ceLve;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/8 8:40
 * @注释 策略模式的具体实现类2
 */
public class IsAllLowerCase implements ValidationStrategy{
    @Override
    public boolean execute(String s) {
        return s.matches("[a-z]+");//匹配一个或多个小写字母  正则表达式
    }

}
