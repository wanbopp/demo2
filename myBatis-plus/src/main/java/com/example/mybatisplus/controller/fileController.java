package com.example.mybatisplus.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// Import necessary packages for 自动导包
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;// The code for starting the Spring Boot application would be outside of this file and not included here.

@RestController
public class fileController {
    
    private static final String FILE_PATH = "D:/example.txt"; // Path to file
    private static final int MAX_READ_WRITE_FREQUENCY = 1000; // Maximum read/write frequency per minute
    private static final AtomicInteger READ_WRITE_COUNTER = new AtomicInteger(0); // Counter for read/write frequency
    
    @PostMapping("/searchFile")
    @ResponseBody
    public String searchFile(@RequestParam("searchString") String searchString) throws IOException {
        if (READ_WRITE_COUNTER.get() >= MAX_READ_WRITE_FREQUENCY) {
            return "Maximum read/write frequency exceeded";
        }
        READ_WRITE_COUNTER.incrementAndGet();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return "File does not exist";
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        boolean found = false;
        while ((line = reader.readLine()) != null) {
            if (line.contains(searchString)) {
                found = true;
                break;
            }
        }
        reader.close();
        if (found) {
            return "Search string found in file";
        } else {
            return "Search string not found in file";
        }
    }
    
}
    
