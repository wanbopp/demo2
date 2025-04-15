package org.example;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author wanbo_pp
 * @Date 2025/4/14 15:39
 * @description: 多线程循环打印的问题
 */
public class AlternatePrint {


    //使用synchronized 实现
//    private static final Object lock = new Object();//共享锁对象
//    private static boolean turnA = true;//控制那个线程打印
//
//    public static void main(String[] args) {
//        Thread threadA = new Thread(() ->
//        {
//            for (int i = 0; i < 5; i++) {
//                synchronized (lock) {
//                    while (!turnA) {//notify 不能控制那个具体的线程执行
//                        //如果当前线程被唤醒，但不该当前线程执行
//                        try {
//                            System.out.println("线程A被唤醒 但当前应当线程B执行 线程A释放锁");
//                            lock.wait();//释放锁
//                        } catch (InterruptedException e) {
//                            Thread.currentThread().interrupt();
//                        }
//                    }
//                    //轮到当前线程打印
//                    System.out.println("A");
//                    //更改顺序
//                    turnA = false;
//                    lock.notify();
//                }
//            }
//        });
//
//        Thread threadB = new Thread(() ->
//        {
//            for (int i = 0; i < 5; i++) {
//                synchronized (lock) {
//                    while (turnA) {
//                        try {
//                            System.out.println("线程B被唤醒 但当前应当线程A执行 线程B释放锁");
//                            lock.wait();
//                        } catch (Exception e) {
//                            Thread.currentThread().interrupt();
//                        }
//                    }
//                    System.out.println("B");
//                    turnA = true;
//                    lock.notify();
//
//                }
//            }
//        });
// t
////       hreadA.start();
//        threadB.start();
//
//    }

    //使用ReentrantLock +  Condition 更灵活适合多个线程交替打印
    //synchronized 只能有一个 monitor ,所有线程wait都在同一个地方
    //用ReentrantLock + Condition ，你可以给每个线程分配单独的等待队列，更加精准
    //更适合多线程交替

    private final Lock lock = new ReentrantLock();
    private final Condition conditionA = lock.newCondition();//A线程的等待队列
    private final Condition conditionB = lock.newCondition();//线程B的等待队列
    private final Condition conditionC = lock.newCondition();//线程C的等待队列
    private int turn = 0;//标志位轮到A打印

    public void printA() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                while (turn % 3 != 0) {
                    conditionA.await();//A线程不该执行时 进入等待队列
                }
                System.out.println("A");
                turn++;
                conditionB.signal();//唤醒B线程
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();//一定要手动释放锁
            }
        }
    }


    public void printB() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                while (turn % 3 != 1) {
                    conditionB.await();//A线程不该执行时 进入等待队列
                }
                System.out.println("B");
                turn++;
                conditionC.signal();//唤醒B线程
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();//一定要手动释放锁
            }
        }
    }

    public void printC() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                while (turn % 3 != 2) {
                    conditionC.await();//A线程不该执行时 进入等待队列
                }
                System.out.println("C");
                turn++;
                conditionA.signal();//唤醒B线程
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();//一定要手动释放锁
            }
        }
    }


    public static void main(String[] args) {
        AlternatePrint alternatePrint = new AlternatePrint();
        new Thread(alternatePrint::printB).start();
        new Thread(alternatePrint::printA).start();
        new Thread(alternatePrint::printC).start();
    }


}
