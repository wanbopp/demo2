package com.example.piccdemo.test.enumSet;

import java.util.EnumSet;
import java.util.Set;

import static java.awt.Font.BOLD;
import static java.awt.Font.ITALIC;
import static java.awt.font.TextAttribute.STRIKETHROUGH;
import static java.awt.font.TextAttribute.UNDERLINE;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/10/8 8:27
 * @注释 用EnumSet代替位域
 */
// Bit field enumeration constants - OBSOLETE! -- 位域枚举常亮 - 废弃
public class Text {
    public static final int STYLE_BOLD = 1 << 0; // 1 加粗
    public static final int STYLE_ITALIC = 1 << 1; // 2 斜体
    public static final int STYLE_UNDERLINE = 1 << 2; // 4 下划线
    public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8 删除线


    // Parameter is bitwise OR of zero or more STYLE_ constants -- 参数是零个或多个STYLE_常量的按位OR
    public void applyStyles(int styles) {
        System.out.println("styles = " + styles);
    }
    //这种表示法让你用 OR 位运算来讲几个常量合并到一个集合中，称为位域。这种表示法有很多缺点。

    //使用EnumSet代替位域
    public enum Style {BOLD, ITALIC, UNDERLINE, STRIKETHROUGH}

    // Any Set could be passed in, but EnumSet is clearly best -- 任何集合都可以传入，但EnumSet显然是最好的
    public void applyStyles(Set<Style> styles) {
        System.out.println("styles = " + styles);
    }

    public static void main(String[] args) {
        System.out.println(1<<0);// 1 等价于 1*2^0
        //0000 0001 -> 0000 0001 左移0位
        System.out.println(1<<1);// 2 等价于 1*2^1
        //0000 0001 -> 0000 0010 左移1位
        System.out.println(1<<2);// 4 等价于 1*2^2
        //0000 0001 -> 0000 0100 左移2位
        System.out.println(1<<3);// 8 等价于 1*2^3
        //0000 0001 -> 0000 1000 左移3位


        Text text = new Text();
        text.applyStyles(STYLE_BOLD | STYLE_UNDERLINE);
        text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));// EnumSet代替位域 EnumSet还提供了丰富的静态工厂来创建集合
    }
}
