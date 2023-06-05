package com.example.streamtest.forkAndJoin;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/31 8:38
 * @注释 实现自己的 Spliterator 可分迭代器
 */
public class WordCounterSpliterator implements Spliterator<Character> {
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        return false;
    }

    @Override
    public Spliterator<Character> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public int characteristics() {
        return 0;
    }

    static final String SENTENCE =
            " Nel mezzo del cammin di nostra vita " +
                    "mi ritrovai in una selva oscura" +
                    " ché la dritta via era smarrita ";

    public static void main(String[] args) {
        //一个迭代式传统数字统计方法
        System.out.println("Found: " + counterWordsIteratively(SENTENCE));

    }

    public static int counterWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) counter++;
                lastSpace = false;
            }
        }
        return counter;
    }
}
