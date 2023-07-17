package com.example.streamtest.newTimeDateApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/17 8:53
 * @注释
 */
public class TemporalAdjusterTest {
    public static void main(String[] args) {
        //使用预定义的TemporalAdjust
        LocalDate now = LocalDate.now();
        //下一个周几、上一个周几
        LocalDate nextFriday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        LocalDate pervious = now.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        System.out.println("nextFriday = " + nextFriday);
        System.out.println("pervious = " + pervious);
        //本月最后一天
        LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("lastDayOfMonth = " + lastDayOfMonth);
        //第n个星期几
        LocalDate dayOfWeekInMonth = now.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.FRIDAY));
        System.out.println("dayOfWeekInMonth = " + dayOfWeekInMonth);
        //本月第一天、下月第一天、明年第一天、当前第一天(及其所有的最后一天)
        LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println("firstDayOfMonth = " + firstDayOfMonth);
        LocalDate firstDayOfNextMonth = now.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println("firstDayOfNextMonth = " + firstDayOfNextMonth);
        LocalDate firstDayOfNextYear = now.with(TemporalAdjusters.firstDayOfNextYear());
        System.out.println("firstDayOfNextYear = " + firstDayOfNextYear);
        LocalDate firstDayOfYear = now.with(TemporalAdjusters.firstDayOfYear());
        System.out.println("firstDayOfYear = " + firstDayOfYear);

        //当前月中第一个符合星期几的日期
        LocalDate firstInMonth = now.with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
        System.out.println("firstInMonth = " + firstInMonth);
        //当月中最后一个符合星期几的日期
        LocalDate lastInMonth = now.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY));
        System.out.println("lastInMonth = " + lastInMonth);

        //next/same
        //返回当前日期之前或者之后，第一个符合星期几要求的日期
        //nextOrSame/previousOrSame
        //返回当前日期之前或者之后，第一个符合星期几要求的日期，如果该日期已经满足要求直接返回该日期


    }
}
