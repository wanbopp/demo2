package com.example.streamtest.refactor.RedesignMode.ceLve;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/8 8:38
 * @注释 策略模式接口  代表某个算法的接口
 */
public interface ValidationStrategy {

    //检验字符串是否符合某个规则
    boolean execute(String s);
}
