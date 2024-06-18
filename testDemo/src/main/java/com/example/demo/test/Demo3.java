package com.example.demo.test;

import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.util.ObjectUtil;
import com.example.demo.model.dto.TrackDto;
import javafx.util.Duration;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/5/10 10:36
 * @注释
 */
public class Demo3 {
    public static void main(String[] args) {
        TrackDto trackDto = new TrackDto();
        System.out.println("trackDto = " + ObjectUtil.isNotNull(trackDto.getTrackerId()));
        System.out.println("Long.valueOf(0).equals(trackDto.getTrackerId()) = " + Long.valueOf(0L).equals(trackDto.getTrackerId()));
        System.out.println("trackDto.getTrackerId() = " + trackDto.getTrackerId());
        String s = "01";
        System.out.println("\"1\".equals(s) = " + "1".equals(s));

        Duration duration = new Duration(111);
        System.out.println("duration = " + duration);


        TrackDto trackDto3 = new TrackDto();
        TrackDto trackDto4 = new TrackDto();
        ArrayList<TrackDto> trackDtos = new ArrayList<>();
        trackDto3.setBuyerId(12323L);
        trackDtos.add(trackDto3);
        trackDto3.setBrandName("999999999999999999");
        trackDtos.forEach(System.out::println);

    }
}
