package org.example;

import java.util.concurrent.Semaphore;

/**
 * @Author wanbo_pp
 * @Date 2025/4/15 10:57
 * @description: 信号量控制循环打印
 */
public class AlternatePrintSemaphore {

    private static final Semaphore semaphoreA = new Semaphore(1);
    private static final Semaphore semaphoreB = new Semaphore(0);


    public static void main(String[] args) {
        //线程A

        Thread threadA = new Thread(() ->
        {
            for (int i = 0; i < 5; i++) {
                try {
                    semaphoreA.acquire();
                    System.out.println("A");
                    semaphoreB.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }


            }
        });


        Thread threadB = new Thread(() ->
        {
            for (int i = 0; i < 5; i++) {
                try {
                    semaphoreB.acquire();
                    System.out.println("B");
                    semaphoreA.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        threadB.start();
        threadA.start();
    }


}
