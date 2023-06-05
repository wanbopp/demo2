package com.example.delete.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.delete.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RestTest {

    private static String lineSeparator = System.lineSeparator();

    @RequestMapping("/test1")
    @ResponseBody
    public String test1( @PathVariable String name) {//使用@Valid接收参数
        return JSONObject.toJSONString(name.endsWith("exe"));
    }


    @RequestMapping("/test2")
    @ResponseBody
    public String test2(@RequestBody Map map) {
        System.out.println("map = " + map);
        map.put("code", "200");
        return JSONObject.toJSONString(map);
    }


    @RequestMapping("/test3")
    @ResponseBody
    public String test3(@PathVariable String map) {
        Map map1 = (Map) JSON.parse(map);

        System.out.println("map = " + map1);
        map1.put("code", "success");
        return JSONObject.toJSONString(map1);
    }


    @RequestMapping(value = "/test4",produces = "application/json;charset=UTF-8")//设置编码
    @ResponseBody
    public String test4(@RequestBody @Valid  Employee employee ,BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            StringBuffer stringBuffer = new StringBuffer();
            bindingResult.getFieldErrors().forEach(fieldError -> {stringBuffer.append(fieldError.getDefaultMessage()+lineSeparator);

            });
            return stringBuffer.toString();
        }
        employee.setBirthDate(new Date());
        return JSONObject.toJSONString(employee);
    }


    @RequestMapping(value = "/test5",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Employee test5(@RequestBody String name){//@PathVariable restful风格获取路径参数
        return new Employee();
    }

}

