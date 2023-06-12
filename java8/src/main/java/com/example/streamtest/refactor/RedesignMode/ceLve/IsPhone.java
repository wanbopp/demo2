package com.example.streamtest.refactor.RedesignMode.ceLve;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/8 8:52
 * @注释
 */
public class IsPhone implements ValidationStrategy{
    @Override
    public boolean execute(String s) {
        return s.matches("(\\d{3})\\d{4}(\\d{4})");
    }
}
