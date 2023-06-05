package com.example.upload.controller;


import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Map;


/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/2/26 11:09
 * @注释
 */
@RestController
public class FileUploadController {
/*
这个实例没有并没有处理大文件的上传和下载，因此需要额外优化
例如将大文件分成多个部分进行上传和下载，以避免内存溢出问题
 */
    private static final String UPLOAD_FOLDER = "uploads/";

    /**
     * resume参数控制是否继续上传，如果为true且文件已经存在，则文件指针定位到文件末尾，并继续上传
     * 如果resume为false或文件不存在 则从头开始
     * @param file
     * @param resume
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam(value = "resume", required = false) Boolean resume) {
        File uploadFile = new File("C://Users//wanbo_pp//Desktop//aim//photo.jpg");
        //判断文件夹是否不存在 不存在就创建
        if (!uploadFile.exists()){
            uploadFile.mkdirs();
        }
        if (resume != null && resume && uploadFile.exists()) {
            long fileSize = uploadFile.length();
            try (RandomAccessFile raf = new RandomAccessFile(uploadFile, "rw")) {
                raf.seek(fileSize);
                copy(file.getInputStream(), raf);
                return ResponseEntity.ok("Resume uploading file" + file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.ok("出现问题");
            }
        }
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("File" + file.getOriginalFilename() + "upload Successfully");
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        File file = new File(UPLOAD_FOLDER + fileName);
        Resource resource = new FileSystemResource(file);

        if (!resource.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    /**
     * 该方法将从输入流中读取
     *
     * @param input
     * @param output
     * @throws IOException
     */
    public static void copy(InputStream input, RandomAccessFile output) throws IOException {
        byte[] buffer = new byte[1024];
        int n;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
    }

}

