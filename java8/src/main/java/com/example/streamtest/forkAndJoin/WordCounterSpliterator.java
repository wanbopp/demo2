package com.example.streamtest.forkAndJoin;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/31 8:38
 * @注释 实现自己的 Spliterator 可分迭代器
 */
public class WordCounterSpliterator implements Spliterator<Character> {

    private final String string;

    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    //是否还有元素需要遍历
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        //处理当前字符
        action.accept(string.charAt(currentChar++));
        return currentChar < string.length();//如果还有字符要处理，则返回true
    }

    //Spliterator拆分
    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;//剩余要处理的字符数目
        if (currentSize < 10) {//如果剩余的字符数目小于10，就返回null表示无需拆分
            return null;
        }
        //将试探拆分位置设定为要解析的String的中间
        for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
            //让拆分位置前进直到下一个空格
            if (Character.isWhitespace(string.charAt(splitPos))) {
                //创建一个新WordCounterSpliterator来解析String从开始到拆分位置的部分
                Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                //将这个WordCounterSpliterator的起始位置设为拆分位置
                currentChar = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    //估算还剩下多少元素需要遍历
    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    //特征值 无需关心 定义拆分的特性
    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }


    //一个迭代式数字统计方法
    public static int counterWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {//遍历String中所有的字符
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) counter++;//上一个字符是空格，而当前遍历的字符串不是空格，单词的计数器加一
                lastSpace = false;
            }
        }
        return counter;
    }


    private int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        //reduce方法的第一个参数是初始值
        //第二个参数是一个BinaryOperator，计算两个WordCounter的部分结果
        //第三个参数是一个函数，将两个WordCounter对象合并成一个
        return wordCounter.getCounter();
    }


    static final String SENTENCE =
            " Nel mezzo del cammin di nostra vita " +
                    "mi ritrovai in una selva oscura" +
                    " ché la dritta via era smarrita ";

    public static void main(String[] args) {
        //一个迭代式传统数字统计方法
        System.out.println("Found: " + counterWordsIteratively(SENTENCE) + " words");
        //Found: 19 words
        //以函数式风格重写的单词计数器
        Stream<Character> characterStream = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);//将String转换为流
//        System.out.println("Found: " + new WordCounterSpliterator().countWords(characterStream) + " words");
        //Found: 19 words


        //以并行流的方式工作
        System.out.println("Found: " + new WordCounterSpliterator(SENTENCE).countWords(characterStream.parallel()) + " words");
        //Found: 80 words (数量不正确)
        //因为并行时 String在任意位置拆分，导致同一个单词被多次统计
        //解决方案：使用自定义Spliterator迭代器  定义拆分的位置必须在两个单词之间


        //使用自定义Spliterator迭代器
        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> stream = StreamSupport.stream(spliterator, true);//创建一个新的流
        System.out.println("Found: " + new WordCounterSpliterator(SENTENCE).countWords(stream) + " words");
        //Found: 19 words

    }
}
