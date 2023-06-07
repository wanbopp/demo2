package com.example.streamtest.refactor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/7 19:33
 * @注释
 */
public class AddFlexible {

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\wanbo_pp\\Desktop\\aim\\新建 文本文档 (2).txt"))) {
            return p.process(br);
        }
    }

    //环绕执行，在准备阶段，使用函数式接口传递行为
    public static void main(String[] args) throws IOException {
        String s = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(s);
        String s1 = processFile(BufferedReader::readLine);
        System.out.println(s1);
    }
}
