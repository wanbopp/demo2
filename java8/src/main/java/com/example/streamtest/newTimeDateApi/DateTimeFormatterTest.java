package com.example.streamtest.newTimeDateApi;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/18 19:06
 * @注释 时间日期格式化器测试
 */
public class DateTimeFormatterTest {
    public static void main(String[] args) {
        LocalDateTime parse = LocalDateTime.parse("2023-07-18T19:08:00");
        System.out.println("parse = " + parse);
        String format = parse.format(DateTimeFormatter.ISO_DATE);
        System.out.println("format = " + format);
        String format1 = parse.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println("format1 = " + format1);

        //通过解析代表时间或者日期的字符串重新创建该日期对象
        LocalDateTime parse1 = LocalDateTime.parse("2023-07-18T19:08:08", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println("parse1 = " + parse1);


        ///按照某个模式创建DateTimeFormatter实例
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDateTime now = LocalDateTime.now();
        String format2 = now.format(dateTimeFormatter);//DateTimeFormatter 解析时可以忽略掉时间/日期
        System.out.println("format2 = " + format2);
        LocalDate parse2 = LocalDate.parse(format2, dateTimeFormatter);
        System.out.println("parse2 = " + parse2);

        //LocalDate的formate方法使用指定的模式生成了一个代表该日期的字符串，紧接着使用LocalDate.parse()方法生成一个LocalDate对象


        //创建一个本地化的DateTimeFomatter
        DateTimeFormatter chinaDateFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.CHINA);
        LocalDate of = LocalDate.of(2023, 7, 18);
        String format3 = of.format(chinaDateFormatter);
        System.out.println("format3 = " + format3);

        //DateTimeFormatterBuilder类 创建更复杂的格式器
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.CHINA);
        String format4 = of.format(formatter);
        System.out.println("format4 = " + format4);

    }
}
