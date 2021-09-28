package com.atguigu.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2020-03-28 15:31
 * 题目：要求如下
 *   多个线程协同干活，要求到最后一个线程为零后，才能启动主线程。
 *   做一个减少一个，清零后才能启动主线程，逐个做减法。
 *
 *   7个同学在一个教室上晚自习，要求班长锁门，必须要最后一个同学都离开教室了，班长才可以
 *   关门走人。
 *
 *    * CountDownLatch主要有两个方法，当一个或多个线程调用await方法时，这些线程会阻塞。
 *  * 其它线程调用countDown方法会将计数器减1(调用countDown方法的线程不会阻塞)，
 *  * 当计数器的值变为0时，因await方法阻塞的线程会被唤醒，继续执行。
 */
public class CountDownLatchDemo
{
    public static void main(String[] args) throws InterruptedException
    {
        // 6个线程模拟自习同学，1个main模拟班长角色关门走人，需要最后

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <=6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t"+"---离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName()+"\t"+"---班长关门走人");
    }
}
