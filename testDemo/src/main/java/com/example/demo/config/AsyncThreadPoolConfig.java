package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
public class AsyncThreadPoolConfig {


    /**
     * 基础线程：核心3 最大5 队列100
     */
    @Bean("async-pool")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        // 创建一个线程池对象
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程池大小
        executor.setCorePoolSize(12);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 队列容量
        executor.setQueueCapacity(100);
        // 允许线程的空闲时间30秒
        executor.setKeepAliveSeconds(30);
        // 线程池名的前缀
        executor.setThreadNamePrefix("async-pool-");
        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);
        // 线程池对拒绝任务的处理策略,当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

//    /**
//     * websocket推送消息：核心8 最大10 队列20
//     */
//    @Bean("websocket-pool")
//    public Executor webSocketTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        // 核心线程池大小
//        executor.setCorePoolSize(20);
//        // 最大线程数
//        executor.setMaxPoolSize(40);
//        // 队列容量
//        executor.setQueueCapacity(20);
//        // 允许线程的空闲时间30秒
//        executor.setKeepAliveSeconds(30);
//        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
//        executor.setAwaitTerminationSeconds(60);
//        // 线程名字前缀
//        executor.setThreadNamePrefix("websocket-pool-");
//        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
//        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        executor.initialize();
//        return executor;
//    }
//
//    /**
//     * 倒计时重置：核心3 最大5 队列20
//     */
//    @Bean("reset-pool")
//    public Executor resetTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        // 核心线程池大小
//        executor.setCorePoolSize(20);
//        // 最大线程数
//        executor.setMaxPoolSize(40);
//        // 队列容量
//        executor.setQueueCapacity(20);
//        // 允许线程的空闲时间30秒
//        executor.setKeepAliveSeconds(30);
//        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
//        executor.setAwaitTerminationSeconds(60);
//        // 线程名字前缀
//        executor.setThreadNamePrefix("reset-pool-");
//        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
//        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        executor.initialize();
//        return executor;
//    }
}