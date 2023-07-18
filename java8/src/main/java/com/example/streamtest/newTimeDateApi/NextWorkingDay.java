package com.example.streamtest.newTimeDateApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.*;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/18 8:54
 * @注释 实现自定义的TemporalAdjuster
 */
public class NextWorkingDay implements TemporalAdjuster {

    //请设计一个NextWorkingDay类，该类实现了TemporalAdjuster接口，能够计算明天
    //的日期，同时过滤掉周六和周日这些节假日。格式如下所示：
    //如果当天的星期介于周一至周五之间，日期向后移动一天；如果当天是周六或者周日，则
    //返回下一个周一。
    @Override
    public Temporal adjustInto(Temporal temporal) {
        int i = temporal.get(ChronoField.DAY_OF_WEEK);
        DayOfWeek of = DayOfWeek.of(i);
        int adjust = 1;
        if (of == DayOfWeek.FRIDAY) {
            adjust = 3;
        } else if (of == DayOfWeek.SATURDAY) {
            adjust = 2;
        }
        //除了周五、周六其他时间的下一个工作日时间都是+1
        return temporal.plus(adjust, ChronoUnit.DAYS);
    }

    public static void main(String[] args) {
        //使用类封装TemporalAdjuster
        LocalDate date = LocalDate.now();
        NextWorkingDay nextWorkingDay = new NextWorkingDay();//这样的对象只需要创建一个就行，不会改变
        LocalDate with = date.with(nextWorkingDay);
        System.out.println("with = " + with);

        //使用Lambda表达式实现上述的TemporalAdjuster
        LocalDate with1 = date.with(temporal -> {
            int i = temporal.get(ChronoField.DAY_OF_WEEK);
            DayOfWeek of = DayOfWeek.of(i);
            int adjust = 1;
            if (of == DayOfWeek.FRIDAY) {
                adjust = 3;
            } else if (of == DayOfWeek.SATURDAY) {
                adjust = 2;
            }
            //除了周五、周六其他时间的下一个工作日时间都是+1
            return temporal.plus(adjust, ChronoUnit.DAYS);
        });
        System.out.println("with1 = " + with1);

        //使用TemporalAdjusters类的静态工厂方法
        TemporalAdjuster temporalAdjuster = TemporalAdjusters.ofDateAdjuster(localDate -> {
            int i = localDate.get(ChronoField.DAY_OF_WEEK);
            DayOfWeek of = DayOfWeek.of(i);
            int adjust = 1;
            if (of == DayOfWeek.FRIDAY) {
                adjust = 3;
            } else if (of == DayOfWeek.SATURDAY) {
                adjust = 2;
            }
            //除了周五、周六其他时间的下一个工作日时间都是+1
            return localDate.plus(adjust, ChronoUnit.DAYS);
        });
        LocalDate with2 = date.with(temporalAdjuster);
        System.out.println("with2 = " + with2);
    }
}
