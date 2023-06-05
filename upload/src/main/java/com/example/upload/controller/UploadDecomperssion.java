package com.example.upload.controller;

import com.example.upload.vo.ReturnResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RequestMapping
@RestController
public class UploadDecomperssion {

    @RequestMapping("upload")
    public ReturnResult uploadDecomperssion() {
        decomperssion("C:\\Users\\wanbo_pp\\Desktop\\1222.zip","C:\\Users\\wanbo_pp\\Desktop\\aim");
        return new ReturnResult<>();
    }

    /**
     * 解压文件
     *
     * @param targetFileName 需要解压的文件
     * @param parent         解压后存放的地方
     */
    private static void decomperssion(String targetFileName, String parent) {
        try {
            // 构造解压流
            ZipInputStream zin = new ZipInputStream(new FileInputStream(targetFileName), Charset.forName("GBK"));
            // 每个需要解压的文件
            ZipEntry entry = null;
            // 解压后文件
            File file = null;
            // 如果有文件且不是文件夹就循环遍历解压
            while ((entry = zin.getNextEntry()) != null) {
                // 设置父路径和子目录
                file = new File(parent, entry.getName());
                // 判断是不是为空的文件夹
                if (entry.getName().endsWith("\\")) {
                    // 创建文件夹并跳出循环
                    file.mkdirs();
                    continue;
                }
                // 判断文件是否存在
                if (!file.exists()) {
                    // System.out.println("qq");
                    // 创建此文件的上级目录
                    new File(file.getParent()).mkdirs();
                }
                // 写入文件
                OutputStream fis = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fis);
                byte[] bytes = new byte[1024];
                int len;
                while ((len = zin.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);
                }
                bos.close();
            }
            System.out.println(file.getAbsolutePath() + "解压成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



