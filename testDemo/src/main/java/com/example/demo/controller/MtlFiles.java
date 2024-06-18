package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/7/18 13:06
 * @注释 多文件上传接口
 */
@RestController
public class MtlFiles {


    @PostMapping("/upload")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files) {
        // 处理上传的文件
        for (MultipartFile file : files) {
            // 处理单个文件，可以根据需求进行保存或其他操作
            // file.getInputStream() 可以获取文件的输入流
            String originalFilename = file.getOriginalFilename();//可以获取文件的原始文件名
            System.out.println("originalFilename = " + originalFilename);
            // ...
        }

        // 处理完毕后进行重定向或返回响应
        return "redirect:/success";

    }

}
