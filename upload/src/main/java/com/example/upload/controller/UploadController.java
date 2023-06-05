package com.example.upload.controller;


import com.example.upload.service.UpLoadService;
import com.example.upload.vo.ReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("user")
public class UploadController {

    @Value("parentPath")
    private String parentPath;
    @Resource
    private UpLoadService upLoadService;

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);


    @RequestMapping("upload")
    public ReturnResult upLoad(@RequestParam("uploadFile") MultipartFile file) {

    return upLoadService.UpLoadfile(file);
    }

    @RequestMapping("uploadZip")
    public ReturnResult  uploadZip(@RequestParam("zipInputStream")MultipartFile file){
        System.out.println("parentPath = " + parentPath);
        ReturnResult returnResult = upLoadService.uploadZip(file, "C:\\Users\\wanbo_pp\\Desktop\\aim");
        return returnResult;
    }



}
