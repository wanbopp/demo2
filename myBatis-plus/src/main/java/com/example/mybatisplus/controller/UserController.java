package com.example.mybatisplus.controller;

import com.example.mybatisplus.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/12 15:08
 * @注释
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("transaction")
    public String Transaction() {
        try {
            userService.TransactionTest();
        } catch (Exception e) {
            log.error("USERCONTROLLER TRANSACTION",e);
        } finally {

        }
        return "异常";
    }
}
