package com.atguigu.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket //资源类，field+method = class
{
    private int number = 50;
    private Lock lock = new ReentrantLock();//可重入锁+独占锁

    /*public synchronized void sale()
    {
        if(number > 0)
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"卖出第："+(number--)+"\t"+" 还剩下："+number);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

    //=======以下是JUC的新知识

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
 * @create 2020-03-25 12:41
 * 三个售票员    卖出      50张票
 * 如何编写企业级需要的工程化代码？
 *
 * 1 高内聚低耦合的前提下，线程      操作      资源类
 */
public class SaleTicketDemo
{
    public static void main(String[] args)//一切程序的入口
    {
        Ticket ticket = new Ticket();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 1; i <=51; i++) {
                    ticket.sale();
                }
            }
        }, "A").start();

        new Thread(() -> { for (int i = 1; i <=51; i++) ticket.sale(); }, "A").start();
        new Thread(() -> { for (int i = 1; i <=51; i++) ticket.sale(); }, "B").start();
        new Thread(() -> { for (int i = 1; i <=51; i++) ticket.sale(); }, "C").start();


        //Thread(Runnable target, String name)  Allocates a new Thread object.

        /*new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 1; i <=51; i++) {
                    ticket.sale();
                }
            }
        }, "A").start();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 1; i <=51; i++) {
                    ticket.sale();
                }
            }
        }, "B").start();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 1; i <=51; i++) {
                    ticket.sale();
                }
            }
        }, "C").start();*/



    }
}
