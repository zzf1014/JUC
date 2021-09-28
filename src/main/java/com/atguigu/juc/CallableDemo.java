package com.atguigu.juc;


import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class MyThread implements Callable<Integer>
{
    @Override
    public Integer call() throws Exception
    {
        System.out.println("----hello 0325");
        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) { e.printStackTrace(); }
        return 9999;
    }
}

/**
 * @auther zzyy
 * @create 2020-03-28 16:30
 * java里面，第三种获得多线程的方式
 * 一个新的接口，Callable
 */
public class CallableDemo
{
    public static void main(String[] args) throws Exception//main + t1
    {
        FutureTask<Integer> futureTask = new FutureTask(new MyThread());
        FutureTask<Integer> futureTask2 = new FutureTask(new MyThread());
        new Thread(futureTask,"t1").start();
        new Thread(futureTask2,"t2").start();

        System.out.println("----------main");

        System.out.println(futureTask.get());
        //System.out.println(futureTask2.get());
    }
}
