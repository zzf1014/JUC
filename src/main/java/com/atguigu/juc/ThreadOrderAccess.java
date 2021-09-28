package com.atguigu.juc;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource//资源类
{
    int flag = 1; //1 ： A    2 ：B   3：C

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();


    public void print5() throws InterruptedException
    {
        lock.lock();
        try
        {
            //1 判断
            while(flag != 1)
            {
                condition1.await();
            }
            //2 干活
            for (int i = 1; i <=5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3 通知
            flag = 2;
            condition2.signal();
        }finally {
            lock.unlock();
        }
    }

    public void print10() throws InterruptedException
    {
        lock.lock();
        try
        {
            //1 判断
            while(flag != 2)
            {
                condition2.await();
            }
            //2 干活
            for (int i = 1; i <=10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3 通知
            flag = 3;
            condition3.signal();
        }finally {
            lock.unlock();
        }
    }
    public void print15() throws InterruptedException
    {
        lock.lock();
        try
        {
            //1 判断
            while(flag != 3)
            {
                condition3.await();
            }
            //2 干活
            for (int i = 1; i <=15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3 通知
            flag = 1;
            condition1.signal();
        }finally {
            lock.unlock();
        }
    }
}

/**
 * @auther zzyy
 * @create 2020-03-26 12:32
 * 多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 *
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * ......三个线程来10轮
 *
 * 精确通知，按照顺序走
 *
 * 1 线程操作资源类
 * 2 判断、干活、通知
 * 3 while
 * 4 判断标志位的修改
 */
public class ThreadOrderAccess
{
    public static void main(String[] args)
    {
        ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int i = 1; i <=10; i++) {
                try {
                    shareResource.print5();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 1; i <=10; i++) {
                try {
                    shareResource.print10();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 1; i <=10; i++) {
                try {
                    shareResource.print15();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
    }
}
