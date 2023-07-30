package com.example.streamtest.newTimeDateApi;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/24 8:28
 * @注释 不同的时区和历法
 */
public class NewZoneId {
    public static void main(String[] args) {
        ZoneId romeZone = ZoneId.of("Europe/Rome");
        System.out.println("romeZone = " + romeZone);

        //将老的TimeZone 时区对象转换为 ZoneId
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        System.out.println("zoneId = " + zoneId);


        //为LocalDateTime LocalDate Instant 添加时区信息得到 ZoneDateTime实例
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(romeZone);
        System.out.println("zonedDateTime = " + zonedDateTime);//添加时区信息

        LocalDate now1 = LocalDate.now();
        ZonedDateTime zonedDateTime1 = now1.atStartOfDay(romeZone);
        System.out.println("zonedDateTime1 = " + zonedDateTime1);
        ZonedDateTime zonedDateTime2 = now1.atStartOfDay(zoneId);
        System.out.println("zonedDateTime2 = " + zonedDateTime2);


        Instant now2 = Instant.now();
        ZonedDateTime zonedDateTime3 = now2.atZone(romeZone);
        System.out.println("zonedDateTime3 = " + zonedDateTime3);


        //通过ZoneID 将 LocalDateTime 转换为 Instant

        LocalDateTime now3 = LocalDateTime.now();
        //Instant instant = now3.toInstant((ZoneOffset) zoneId);

        //通过反向的方式得到LocalDateTime
        Instant now4 = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now4, romeZone);
        System.out.println("localDateTime = " + localDateTime);



//        LocalTime time = LocalTime.of(8, 0, 0);
//        String format = LocalDate.now().atTime(L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        System.out.println("format = " + format);
//        //format = 2023-07-24 08:00:00


        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime time = LocalTime.of(8, 0, 0);
        LocalDateTime localDateTime1 = dateTime.toLocalDate().atTime(time);
//        LocalDate.



    }

    public void test() {
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        // 设置小时为8
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        // 设置分钟为0
        calendar.set(Calendar.MINUTE, 0);
        // 设置秒数为0
        calendar.set(Calendar.SECOND, 0);
        // 设置毫秒数为0
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 获取新的时间
        Date date = calendar.getTime();
        String format1 = format.format(date);
        // 输出新的时间
        System.out.println(format1);
        System.out.println(format1);
        System.out.println(format1);
    }



}
