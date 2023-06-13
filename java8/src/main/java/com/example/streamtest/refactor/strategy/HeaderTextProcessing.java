package com.example.streamtest.refactor.strategy;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 16:55
 * @注释
 */
public class HeaderTextProcessing extends ProcessingObject<String>{

    @Override
    protected String handleWork(String input) {
        return "From Raoul, Mario and Alan: " + input;
    }

}
