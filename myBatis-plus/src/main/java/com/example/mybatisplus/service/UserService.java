package com.example.mybatisplus.service;

import com.example.mybatisplus.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wanbo_pp
*/
public interface UserService extends IService<User> {

    void TransactionTest();

}
