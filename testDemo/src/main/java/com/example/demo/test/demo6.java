package com.example.demo.test;

import cn.hutool.core.lang.hash.Hash;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.example.demo.model.po.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/9/21 16:22
 * @注释
 */
public class demo6 {

    public static void main(String[] args) {



        Employee employee = new Employee("NO.1", "18", "1");
        Employee employee1 = new Employee("NO.2", "19", "1");
        Employee employee2 = new Employee("NO.3", "20", "1");
        Employee employee3 = new Employee("NO.4", "21", "1");
        Employee employee4 = new Employee("NO.5", "22", "1");
        Employee employee5 = new Employee("NO.6", "23", "1");
        List<Map<String,String>> list = new ArrayList<>();
        //对象转换为map
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.convertValue(employee, Map.class);
        Map map1 = objectMapper.convertValue(employee1, Map.class);
        Map map2 = objectMapper.convertValue(employee2, Map.class);
        Map map3 = objectMapper.convertValue(employee3, Map.class);
        Map map4 = objectMapper.convertValue(employee4, Map.class);
        Map map5 = objectMapper.convertValue(employee5, Map.class);

        list.add(map);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);


        //Employee创建 6个
        Employee employee6 = new Employee("NO.1", "18", "2");
        Employee employee7 = new Employee("NO.2", "19", "1");
        Employee employee8 = new Employee("NO.3", "20", "1");
        Employee employee9 = new Employee("NO.4", "21", "1");
        Employee employee10 = new Employee("NO.5", "22", "1");
        Employee employee511= new Employee("NO.6", "23", "1");

        Map map6 = objectMapper.convertValue(employee, Map.class);
        Map map7 = objectMapper.convertValue(employee1, Map.class);
        Map map8 = objectMapper.convertValue(employee2, Map.class);
        Map map9 = objectMapper.convertValue(employee3, Map.class);
        Map map10 = objectMapper.convertValue(employee4, Map.class);
        Map map11 = objectMapper.convertValue(employee5, Map.class);
        List<Map<String,String>> list2 = new ArrayList<>();
        list2.add(map);
        list2.add(map1);
        list2.add(map2);
        list2.add(map3);
        list2.add(map4);
        list2.add(map5);



        //通过name关联 找到gender不等的
        //对list2进行分组，map.get("name") 进行分组
        Map<Object, List<Map>> relation = list2.parallelStream().collect(Collectors.groupingBy(m -> m.get("name")));
        final Map<String, Object> different = new HashMap<>();
        list.forEach(m -> {
            List<Map> name = relation.get(m.get("name"));
            if (!name.get(0).get("gender").equals(m.get("gender"))){
                different.put((String) m.get("name"),m.get("gender"));
                //找到两个集合中 键值对 对象name相同但是年龄不同的
            }
        });

        different.entrySet().forEach(m -> {
            System.out.println("m.getKey() = " + m.getKey());
            System.out.println("m.getValue() = " + m.getValue());
        });


    }

}
