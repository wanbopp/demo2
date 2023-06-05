package com.example.streamtest.entity;

import com.sun.org.apache.xpath.internal.objects.XString;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/3/21 14:43
 * @注释
 */

public class Trader {

    private final String name;
    private final String city;

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Trader{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
