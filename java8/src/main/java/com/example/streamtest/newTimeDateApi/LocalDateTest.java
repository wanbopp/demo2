package com.example.streamtest.newTimeDateApi;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/11 19:26
 * @注释 LocalDate测试
 */
public class LocalDateTest {
    public static void main(String[] args) {
        //创建LocalDate对象，并读取值
        //使用静态工厂方法of
        LocalDate localDate = LocalDate.of(2023, 7, 11);
        System.out.println("localDate = " + localDate);
        int year = localDate.getYear();//获取年
        System.out.println("year = " + year);
        Month month = localDate.getMonth();//获取月
        System.out.println("month = " + month);
        int dayOfMonth = localDate.getDayOfMonth();//这个月第几天
        System.out.println("dayOfMonth = " + dayOfMonth);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();//这天周几
        System.out.println("dayOfWeek = " + dayOfWeek);
        int monthValue = localDate.getMonthValue();//第几个月
        System.out.println("monthValue = " + monthValue);

        //LocalDate.now() 获取当前时间
        LocalDate now = LocalDate.now();
        System.out.println("now = " + now);



        //使用TemporalField读取LocalDate的值
        //TemporalField是一个接口 ChronoField 枚举实现了这个接口
        int i = localDate.get(ChronoField.YEAR);
        System.out.println("i = " + i);
        int i1 = localDate.get(ChronoField.MONTH_OF_YEAR);
        int i2 = localDate.get(ChronoField.DAY_OF_MONTH);
        System.out.println("i2 = " + i2);


        //创建localtime并读取值
        LocalTime localTime = LocalTime.of(8, 26, 30);
        System.out.println("localTime = " + localTime);
        int hour = localTime.getHour();
        System.out.println("hour = " + hour);
        int minute = localTime.getMinute();
        System.out.println("minute = " + minute);
        int second = localTime.getSecond();
        System.out.println("second = " + second);
        LocalTime now1 = LocalTime.now();
        System.out.println("now1 = " + now1);


        //LocalDate和LocalTime都可以解析对象
        LocalDate parse = LocalDate.parse("2023-07-12");
        System.out.println("parse = " + parse);
        LocalTime parse1 = LocalTime.parse("08:54:31");
        System.out.println("parse1 = " + parse1);
        //可以传格式化 DateTimeFormatter也声明几种国际格式
        LocalDate parse2 = LocalDate.parse("2023/07/12", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        System.out.println("parse2 = " + parse2);
        //如果参数解析失败，会抛出RuntimeException的DateTimeParseException


        //时间日期合并LocalDateTime
        LocalDateTime now2 = LocalDateTime.now();
        System.out.println("now2 = " + now2);
        LocalDateTime of = LocalDateTime.of(localDate, localTime);
        System.out.println("of = " + of);
        LocalDateTime parse3 = LocalDateTime.parse("2023-07-11T08:26:30");//通时也支持格式化器
        System.out.println("parse3 = " + parse3);

        //LocalDateTime解析出LocalDate和LocalTime
        LocalDate localDate1 = now2.toLocalDate();
        System.out.println("localDate1 = " + localDate1);
        LocalTime localTime1 = now2.toLocalTime();
        System.out.println("localTime1 = " + localTime1);


        LocalDateTime parse4 = LocalDateTime.parse("20230710192945", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        System.out.println("parse4 = " + parse4);


    }
}
