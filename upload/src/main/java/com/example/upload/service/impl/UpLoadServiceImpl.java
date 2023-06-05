package com.example.upload.service.impl;

import com.example.upload.service.UpLoadService;
import com.example.upload.vo.ReturnResult;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class UpLoadServiceImpl implements UpLoadService {

    private static final Logger log = LoggerFactory.getLogger(UpLoadServiceImpl.class);

    @Override
    public ReturnResult UpLoadfile(MultipartFile file) {
        String path = "C:\\upload";
//        String fileName = UUID.randomUUID().toString().replace("-", "");
        String fileName = file.getOriginalFilename();
        File targetFile = new File(path + File.separator + fileName);
        if (!targetFile.exists()) {
            targetFile.getParentFile().mkdir();//判断并创建上级目录
            try {
                targetFile.createNewFile();//根据文件名创建文件
            } catch (IOException e) {
                log.error("", e);
            }
        }

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(targetFile);
            IOUtils.copy(file.getInputStream(), fileOutputStream);
            log.info("======================上传成功");
        } catch (FileNotFoundException e) {
            log.error("", e);
            return new ReturnResult<>("500", "上传出错");
        } catch (IOException e) {
            log.error("", e);
            return new ReturnResult<>("500", "上传出错");

        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }

        return new ReturnResult("200", "上传成功", null);
    }

    @Override
    public ReturnResult uploadZip(MultipartFile targetFile, String parent) {
        String name = targetFile.getName();
        InputStream  inputStream = null;
        ZipInputStream zipInputStream = null ;

        try {

             inputStream = targetFile.getInputStream();//通过file获取写入流
             zipInputStream = new ZipInputStream(inputStream, Charset.forName("GBK"));//拿到解压输入流

            ZipEntry zipEntry = null;//每一个需要解压的文件
            File file = null; //解压后的文件

//        开始遍历，如果有文件且不是文件夹，就遍历循环解压
            while (((zipEntry = zipInputStream.getNextEntry()) != null)) {

                String child = zipEntry.getName();
                

                int lastIndexOf = child.lastIndexOf(".");
                String suffix = child.substring(lastIndexOf+1);//获取文件名后缀

                file = new File(parent+ File.separator +suffix+File.separator+zipEntry.getName());//设置父路径和子目录
                if (zipEntry.getName().endsWith("\\")) {//判断是不是空的文件夹,空文件夹创建并跳出本次循环
                    file.mkdirs();
                    continue;
                }

                //判断文件是否存在
                if (!file.exists()) {
                    //创建此文件的上级目录
                    new File(file.getParent()).mkdirs();
                }
                //写入文件
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                byte[] bytes = new byte[1024];
                int len;
                while ((len = zipInputStream.read(bytes)) != -1) {
                    bufferedOutputStream.write(bytes, 0, len);
                }
                bufferedOutputStream.close();
//                开始读取密码
                if (suffix.equals("txt")){
                    String password = getPassword(file);
                    System.out.println("password = " + password);
                }

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.info("**************上传失败*************");
            return new ReturnResult<>("500", "失败");

        } catch (IOException e) {
            e.printStackTrace();
            log.info("**************上传失败*************");
            return new ReturnResult<>("500", "失败");

        }finally {
            try {
                inputStream.close();
                zipInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String certificateId = targetFile.getOriginalFilename().substring(0,targetFile.getOriginalFilename().lastIndexOf("."));
        System.out.println("certificateId = " + certificateId);
        return new ReturnResult<>("200", "上传成功");

    }

    private static String getPassword(File file){
        String password;
        InputStream inputStream = null;
        try {
             inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
             inputStream.read(bytes);
            String s = new String(bytes);
            int lastIndexOf = s.lastIndexOf(":");
             password = s.substring(lastIndexOf+1);//截取 密码

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return password;
    }



}