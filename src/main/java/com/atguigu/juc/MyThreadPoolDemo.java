package com.atguigu.juc;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class FlightTicket //资源类，field+method = class
{
    private int number = 50;
    private Lock lock = new ReentrantLock();//可重入锁+独占锁

    public void sale()
    {
        lock.lock();
        try
        {
            if(number > 0)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"卖出第："+(number--)+"\t"+" 还剩下："+number);
            }
        }finally {
            lock.unlock();
        }
    }
}


/**
 * @auther zzyy
 * @create 2020-03-30 14:15
 */
public class MyThreadPoolDemo
{
    public static void main(String[] args)
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池5线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //ExecutorService threadPool = Executors.newCachedThreadPool();

        try
        {
            for (int i = 1; i <=20; i++) {
                int finalI = i;
                threadPool.submit(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t"+"员工服务第："+ finalI +"顾客");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
