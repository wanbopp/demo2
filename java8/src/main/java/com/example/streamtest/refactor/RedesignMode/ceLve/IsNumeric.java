package com.example.streamtest.refactor.RedesignMode.ceLve;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/8 8:41
 * @注释  策略模式的具体实现类1
 */
public class IsNumeric implements ValidationStrategy{
    @Override
    public boolean execute(String s) {
        return s.matches("\\d+");//匹配一个或多个数字  正则表达式
    }
}
