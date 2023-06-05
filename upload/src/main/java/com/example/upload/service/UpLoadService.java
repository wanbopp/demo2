package com.example.upload.service;

import com.example.upload.vo.ReturnResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.zip.ZipInputStream;

public interface UpLoadService {

    ReturnResult UpLoadfile(MultipartFile file);

    ReturnResult uploadZip(MultipartFile file, String parent);

}
