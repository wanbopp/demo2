package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/3/2 20:45
 * @注释
 */
@Controller
public class ChatController {
    private static final String URL = "https://api.openai.com/v1/engines/davinci-codex/completions";

    private static final String APIKEY = "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";

    @RequestMapping("/chatGPT")
    @ResponseBody
    public ResponseEntity ChatController(@RequestParam("prompt") String prompt) {

        String inputLine;
        try {
            URL obj = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // 设置请求方法为POST
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + prompt);
            // 设置请求体
            String postData = "{\"prompt\":\"" + APIKEY + "\",\"max_tokens\":50,\"temperature\":0.7}";
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(postData);


            // 发送请求并获取响应
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok("系统异常");
        }

        return ResponseEntity.ok(inputLine);
    }
}