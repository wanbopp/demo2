package com.example.streamtest.refactor.strategy;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 16:56
 * @注释
 */
public class SpellCheckerProcessing extends ProcessingObject<String>{

    @Override
    protected String handleWork(String input) {
        return input.replaceAll("labda","lambda");
    }


}
