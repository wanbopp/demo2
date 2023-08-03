package com.example.mybatisplus.test;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mybatisplus.dao.UserMapper;
import com.example.mybatisplus.domain.User;
import com.example.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;


    @Resource
    private UserService userService;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void testAllEq() {
        System.out.println(("----- AllEq method test ------"));


        List<User> users = userService.list(Wrappers.lambdaQuery(User.class).ge(User::getCreateTime, new Date()));
        System.out.println("users = " + users);
        Date date = new Date();
        System.out.println("date = " + date);
        QueryWrapper<User> query = Wrappers.query();
        query.ge("create_time",date);
        List<User> users1 = userService.list(query);
//
////        HashMap<String, String> map = new HashMap<>();
////        map.put("id","1");
////        map.put("name","jone");
////        map.put("age","18");
////        QueryWrapper wrapper = Wrappers.query().allEq(map,false);
////        //TODO 进行对象匹配  null2IsNull 参数可以控制过滤map中value为null的值
////        userMapper.selectList(wrapper).forEach(System.out::println);
////
////        QueryWrapper wrapper1 = Wrappers.query().allEq((k,v) -> v.equals("18"),map,false);
////        //使用BiPredicate<R, V> filter 过滤函数,是否允许字段传入比对条件中 (k,v) -> v.equals("18")返回为true时传入对比条件
////        userMapper.selectList(wrapper1).forEach(System.out::println);
//
////        QueryWrapper wrapper2 = Wrappers.query().ne("age",18);
////        //ne 不等于
////        userMapper.selectList(wrapper2).forEach(System.out::println);
//        HashMap<String, String> map1 = new HashMap<>();
////        type：1、2
////        duty：all、member、area
////        refund：false、ture
////        penalSum:false、ture
////        compensation:
////        map1.put("type","1");
////        map1.put("duty","all");
////        map1.put("refund","false");
////        map1.put("penalSum","false");
////        map1.put("compensation","false");
////        User user = new User();
////        user.setName("JSON");
////        user.setEmail(JSONObject.toJSONString(map1));
////        userMapper.insert(user);
////        User user = userMapper.selectById("1645381655890739202");
////        Map map = JSONObject.parseObject(user.getEmail(), Map.class);
////        Object type = map.get("type");
////        System.out.println("type = " + type.toString());
////        Object duty = map.get("duty");
////        System.out.println("duty = " + duty.toString());
////        Object refund = map.get("refund");
////        System.out.println("refund = " + refund.toString());
//        User user = userMapper.selectById("9");
//
//        Assert.notNull(user,"buweikon");
//
//
//        boolean update = userService.update(Wrappers.lambdaUpdate(User.class)
//                .set(User::getAge, 18)
//                .set(User::getCreateTime, new Date())
//                .set((1 == 1), User::getCreateTime, new Date()));
//
//        System.out.println("update = " + update);
    }

}