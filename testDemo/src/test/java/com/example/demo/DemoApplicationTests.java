package com.example.demo;

import com.example.demo.model.po.Dish;
import com.example.demo.service.DishService;
import com.example.demo.test.AsyncRun;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    @Qualifier(value = "async-pool")
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private AsyncRun asyncRun;


    @Autowired
    private DishService dishService;

    @Test
    void contextLoads() {
    }


    @Test
    @Async
    void asyn() {
        // 创建多个任务对象
        Runnable task1 = () -> System.out.println("Task 1 executed.");
        Runnable task2 = () -> System.out.println("Task 2 executed.");
        Callable<String> task3 = () -> {
            Thread.sleep(1000);
            return "Task 3 executed.";
        };

		executor.execute(task1);
		executor.execute(task2);
        executor.execute(asyncRun);
        Future<String> submit = executor.submit(task3);

        // 等待任务执行完成
        try {
            System.out.println("submit = " + submit.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        Dish dish = new Dish();
        dish.setName("111");
        executor.execute(() -> {
            dishService.save();
            System.out.println("dish = " + dish);
        });

    }


    @Test
    void getSteam() throws IOException {
        URL url = new URL("https://47.115.216.207:8888/root/file/text.slsx");
        // 打开网络连接并获取输出流
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
    }


}
