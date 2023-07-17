package com.example.streamtest.newTimeDateApi;

import java.time.*;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.ChronoUnit;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/12 19:44
 * @注释
 */
public class DurationOrPeriod {
    public static void main(String[] args) {
        //我们需要创建两个Temporal对象之间的Duration对象
        //Duration的between方法就是为了这个目的设置
        //between支持两个LocalTime、两个LocalDateTimes、或者两个Instant对象作为参数
        Duration between = Duration.between(LocalTime.now(), LocalTime.of(8, 6, 5));
        System.out.println("between = " + between);
        //Duration between2 = Duration.between(LocalDate.now(), LocalDate.of(2023, 7, 13));//不支持两个LocalDate
        //System.out.println("between2 = " + between2);
        Duration between1 = Duration.between(Instant.now(), Instant.ofEpochMilli(2222222222222L));
        System.out.println("between1 = " + between1);


        //Period类的使用
        //Period类的使用和Duration类似，也是用于计算两个日期之间的差值
        //Period类的between方法支持两个LocalDate对象作为参数
        //Period类的between方法返回的是两个日期之间的差值,可以对年月日进行建模
        Period period = Period.between(LocalDate.of(2019, 6, 20), LocalDate.now());
        System.out.println("period = " + period);//两个日期间隔四年、0个月、27天
        System.out.println("period.getYears() = " + period.getYears());
        System.out.println("period.getMonths() = " + period.getMonths());
        System.out.println("period.getDays() = " + period.getDays());

        Period from = Period.from(ChronoPeriod.between(LocalDate.of(2019, 6, 20), LocalDate.now()));
        System.out.println("from.get() = " + from.get(ChronoUnit.YEARS));
        System.out.println("from.get() = " + from.get(ChronoUnit.MONTHS));
        System.out.println("from.get() = " + from.get(ChronoUnit.DAYS));



    }
}
