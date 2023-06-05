package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.service.UserService;
import com.example.mybatisplus.domain.User;
import com.example.mybatisplus.dao.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author wanbo_pp
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-04-03 19:29:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




