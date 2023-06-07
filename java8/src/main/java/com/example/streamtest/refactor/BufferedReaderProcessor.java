package com.example.streamtest.refactor;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/7 19:47
 * @注释  函数式接口
 */
@FunctionalInterface
public interface BufferedReaderProcessor {

    String process(BufferedReader b) throws IOException;
}
