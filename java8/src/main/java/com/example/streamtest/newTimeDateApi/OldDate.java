package com.example.streamtest.newTimeDateApi;

import java.util.Calendar;
import java.util.Date;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/11 8:35
 * @注释 旧的日期时间API
 */
public class OldDate {
    public static void main(String[] args) {
        //Date表示Java 8的发布日期
        Date date = new Date(114, Calendar.MARCH, 8);
        System.out.println("date = " + date);
        boolean a = Character.isDigit('9');
        System.out.println("a = " + a);
    }
}
