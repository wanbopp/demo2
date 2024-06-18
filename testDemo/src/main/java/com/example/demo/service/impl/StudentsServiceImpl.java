package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Students;
import com.example.demo.mapper.StudentsMapper;
import com.example.demo.service.StudentsService;
import org.springframework.stereotype.Service;

/**
* @author wanbo_pp
* @description 针对表【students】的数据库操作Service实现
* @createDate 2023-06-14 14:03:13
*/
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Students>
    implements StudentsService {

}




