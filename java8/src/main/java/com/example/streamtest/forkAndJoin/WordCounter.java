package com.example.streamtest.forkAndJoin;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/6 8:58
 * @注释 一个用在遍历Character流时计数的类（统计的是单词数）
 */
public class WordCounter {
    private final int counter;//计数器

    private final boolean lastSpace;//上一个字符串是否为空


    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    public WordCounter accumulate(Character c) {
        if (Character.isWhitespace(c)) {
            //和迭代方法一样 accumulate方法一个一个遍历 character
            return lastSpace ? this : new WordCounter(counter, true);
        } else {
            //上一个字符是空格，而当前遍历的字符不是空格时，计数器加一，lastSpace置为false。
            return lastSpace ? new WordCounter(counter + 1, false) : this;
        }
    }

    //合并两个WordCounter，把其计数器加起来
    public WordCounter combine(WordCounter wordCounter) {
        return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
        //仅需要计数器的总和，无需关系lastSpace
    }

    public int getCounter() {
        return counter;
    }
}

