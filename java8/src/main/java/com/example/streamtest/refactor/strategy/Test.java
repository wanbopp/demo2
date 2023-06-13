package com.example.streamtest.refactor.strategy;

import java.util.function.UnaryOperator;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/12 16:57
 * @注释
 */
public class Test {

    public static void main(String[] args) {
        ProcessingObject<String> headerTextProcessing = new HeaderTextProcessing();
        ProcessingObject<String> spellCheckerProcessing = new SpellCheckerProcessing();

        headerTextProcessing.setSuccessor(spellCheckerProcessing);//链接起来

        String result = headerTextProcessing.handle("Aren't labdas really sexy?!!");
        System.out.println(result);

        //使用lambda表达式
        //UnaryOperator 将两个操作链接起来
        UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
        UnaryOperator<String> spellCheckerProcessing1 = (String text) -> text.replaceAll("labda","lambda");

        headerProcessing.andThen(spellCheckerProcessing1);
        String result1 = headerProcessing.apply("Aren't labdas really sexy?!!");
        System.out.println(result1);
    }
}
