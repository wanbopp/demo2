package com.example.streamtest.newTimeDateApi;

import java.time.*;
import java.time.temporal.ChronoField;
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


    }
}
