package com.atguigu.juc;

import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2021-07-12 10:42
 */
public class DeadLockDemo
{
    static Object lockA = new Object();
    static Object lockB = new Object();

    public static void main(String[] args)
    {
        new Thread(() -> {
            synchronized (lockA)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"自己持有A锁，希望获得B锁");
                //暂停几秒钟线程
                try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (lockB)
                {
                    System.out.println(Thread.currentThread().getName()+"\t"+"获得B锁成功");
                }
            }

        },"A").start();

        new Thread(() -> {
            synchronized (lockB)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"自己持有B锁，希望获得A锁");
                //暂停几秒钟线程
                try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (lockA)
                {
                    System.out.println(Thread.currentThread().getName()+"\t"+"获得A锁成功");
                }
            }

        },"B").start();
    }
}
