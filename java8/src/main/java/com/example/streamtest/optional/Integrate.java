package com.example.streamtest.optional;

import java.util.Optional;
import java.util.Properties;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/15 11:01
 * @注释 Optional 整合测试
 */
public class Integrate {
    //Properties类是Hashtable的子类，该对象用来处理属性文件
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        int a = functionalReadDuration(props, "c");
        System.out.println("a = " + a);
    }

    //以命令式编程方式读取属性
    public int readDuration(Properties props, String name) {
        String property = props.getProperty(name);
        if (property != null) {//确保属性值存在
            try {
                int i = Integer.parseInt(property);
                if (i > 0) {
                    return i;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    //使用Optional重写
    public static int functionalReadDuration(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .flatMap(Integrate::stringToInt)
                .filter(i -> i > 0)
                .orElse(0);
    }

    //使用工具封装转换为optional
    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
