package com.example.demo.test;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/4/18 18:49
 * @注释
 */
@Component
public class AsyncRun implements Runnable{

    int n ;
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            n = n+i;
        }
        System.out.println(n);
    }

    //URL 获取流
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://47.115.216.207:8888/root/file/text.slsx");
        // 打开网络连接并获取输出流
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(123);
    }
}
