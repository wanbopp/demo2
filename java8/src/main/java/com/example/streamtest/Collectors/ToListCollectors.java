package com.example.streamtest.Collectors;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/4 19:31
 * @注释
 */
public class ToListCollectors<T> implements Collector<T, List<T>,List<T>> {
    //使用双重检查锁实现单例
    //声明+volatile修饰
    private static  volatile  ToListCollectors toListCollectors;
    //构造函数私有化
    private ToListCollectors(){};
    //获取对象的静态方法
    public static ToListCollectors getToListCollectors(){
        if (toListCollectors == null){
            synchronized(ToListCollectors.class){
                if (toListCollectors == null){
                    toListCollectors = new ToListCollectors();
                }
            }
        }
        return toListCollectors;
    }

    //创建集合操作的起点
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }
    //累计遍历过的项目，原位累加器的内容合并
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }
    //修改第一个累加器，将其与第二个累加器合并返回第一个修改后的累加器
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1,list2) -> {list1.addAll(list2);return list1;};
    }
    //恒等函数  将结果转换为另外一个结果
    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();//List  to  List 返回自身
    }
    //添加标识
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH,Characteristics.CONCURRENT));
    }
}
