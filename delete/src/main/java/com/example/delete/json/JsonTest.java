package com.example.delete.json;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class JsonTest {
//    通过原生生成JSON格式
    public static void test1(){
        JSONObject object = new JSONObject();
        object.put("name","zhangsan");
        object.put("age","18");
        object.put("weight ","73KG");
        HashMap<Object, Object> map = new HashMap<>();
        map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        object.put("map",map);
        System.out.println("object.toJSONString() = " + object.toJSONString());
        System.out.println("object.toString() = " + object.toString());
    }
//

    public static void main(String[] args) {
        test1();
    }

}
