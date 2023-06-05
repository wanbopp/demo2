package com.example.streamtest.upload;


import org.springframework.stereotype.Controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Controller
public class FileUploader {
    private static final int BUFFER_SIZE = 1024 * 1024; // 每个文件块的大小为1MB


    public void upload(URL url, File file) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        long fileSize = file.length();

        String boundary = UUID.randomUUID().toString();
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        long uploadedBytes = 0;
        long startByte = 0;

        // 检查服务器上已经存在的文件块
        if (fileSize > BUFFER_SIZE) {
            String range = connection.getHeaderField("Range");
            if (range != null && range.startsWith("bytes=")) {
                String[] parts = range.substring("bytes=".length()).split("-");
                startByte = Long.parseLong(parts[1]) + 1;
                uploadedBytes = startByte;
            }
        }

        // 记录上传进度
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.skip(startByte);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer, 0, BUFFER_SIZE)) > 0) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(("--" + boundary + "\r\n").getBytes());
                outputStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
                outputStream.write(("Content-Type: application/octet-stream\r\n\r\n").getBytes());
                outputStream.write(buffer, 0, bytesRead);
                outputStream.write("\r\n".getBytes());
                outputStream.flush();
                outputStream.close();
                uploadedBytes += bytesRead;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 完成文件上传
        connection.getOutputStream().write(("--" + boundary + "--\r\n").getBytes());
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            System.out.println("文件上传成功！");
        } else {
            System.out.println("文件上传失败，错误代码: " + responseCode);
        }
    }
}


