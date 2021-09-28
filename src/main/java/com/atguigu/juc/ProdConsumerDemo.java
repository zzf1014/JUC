package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AirCondition //空调，资源类
{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /*public synchronized void increment() throws InterruptedException
    {
        //1 判断
        while(number != 0)
        {
            this.wait(); // A  C
        }
        //2 干活
        System.out.println(Thread.currentThread().getName()+"\t"+(++number));
        //3 通知
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException
    {
        //1 判断
        while(number == 0)
        {
            this.wait();
        }
        //2 干活
        System.out.println(Thread.currentThread().getName()+"\t"+(--number));
        //3 通知
        this.notifyAll();
    }*/

    public void increment() throws InterruptedException
    {
        lock.lock();
        try
        {
            //1 判断
            while(number != 0)
            {
                condition.await();//this.wait();
            }
            //2 干活
            System.out.println(Thread.currentThread().getName()+"\t"+(++number));
            //3 通知
            condition.signalAll();//this.notifyAll();
        }finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException
    {
        lock.lock();
        try
        {
            //1 判断
            while(number == 0)
            {
                condition.await();//this.wait();
            }
            //2 干活
            System.out.println(Thread.currentThread().getName()+"\t"+(--number));
            //3 通知
            condition.signalAll();//this.notifyAll();
        }finally {
            lock.unlock();
        }
    }
}


/**
 * @auther zzyy
 * @create 2020-03-26 11:41
 *
 * 有一个初始值为零的变量，现有两个线程对该变量操作，
 * 实现一个线程对变量加1，实现一个线程对变量减1,交替来10轮
 *
 * 1 高内聚低耦合的前提下，线程       操作    资源类
 * 2 多线程交互和通信，判断    干活  通知
 * 3 防止虚假唤醒,while判断wait使用
 */
public class ProdConsumerDemo
{
    public static void main(String[] args)
    {
        AirCondition airCondition = new AirCondition();

        new Thread(() -> {
            for (int i = 1; i <=10; i++) {
                try {
                    Thread.sleep(200);
                    airCondition.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 1; i <=10; i++) {
                try {
                    Thread.sleep(300);
                    airCondition.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 1; i <=10; i++) {
                try {
                    Thread.sleep(400);
                    airCondition.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(() -> {
            for (int i = 1; i <=10; i++) {
                try {
                    Thread.sleep(500);
                    airCondition.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}
