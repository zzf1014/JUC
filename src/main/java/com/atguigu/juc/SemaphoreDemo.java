package com.atguigu.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2020-03-28 15:51
 * 信号灯的意思。
 *  在信号量上我们定义两种操作：
 *  * acquire（获取） 当一个线程调用acquire操作时，它要么通过成功获取信号量（信号量减1），
 *  *       要么一直等下去，直到有线程释放信号量，或超时。
 *  * release（释放）实际上会将信号量的值加1，然后唤醒等待的线程。
 *  *
 *  * 信号量主要用于两个目的，一个是用于多个共享资源的互斥使用，另一个用于并发线程数的控制。
 *
 * 案例，用争车位来表示
 */
public class SemaphoreDemo
{
    public static void main(String[] args)
    {
        Semaphore semaphore = new Semaphore(30);//6车抢夺3车位

        for (int i = 1; i <=6; i++) {
            int finalI = i;
            new Thread(() -> {
                boolean flag = false;
                try {
                    semaphore.acquire();
                    flag = true;
                    System.out.println(Thread.currentThread().getName()+"\t"+"抢到了车位："+ finalI);
                    //暂停几秒钟线程
                    try { TimeUnit.SECONDS.sleep(new Random().nextInt(4)+1); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName()+"\t"+"离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if(flag == true)
                    {
                        semaphore.release();
                    }
                }
            },String.valueOf(i)).start();
        }
    }
}
//信号量