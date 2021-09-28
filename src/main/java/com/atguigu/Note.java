package com.atguigu;

/**
 * @auther zzyy
 * @create 2021-07-08 22:51  维度？维度？维度？维度？维度？维度？
 *
 * 面试：谈资
 *
 * 1 是什么
 * 2 能干嘛(过去的老技术为什么搞不定？新技术解决了哪些痛点？好处)
 * 3 去哪下(凡技术必须登录官网)
 * 4 怎么玩（业内通用+常用的）
 * 5 永远的HelloWorld
 *   SSM    WWH
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *===============================================
 * 1 什么是JUC
java.util.concurrent
java.util.concurrent.atomic
java.util.concurrent.locks
 *
 *
 *
 * 2 线程、进程，请举例说明TDDL
 *
 *
 * 3 什么是并发、什么是并行，请举例
 *   concurrency，并发
 *   parallel,并行

 *
 * 4 什么是同步、异步、请举例
 *   异步回调

 *
 * 5 开始编码多线程了，请问，java里面获得多线程的方式有几种？
 *   5.1 传统的老知识，两种， extends Thread |  implements Runnable
 *   5.2 新技能+新知识
 */
public class Note
{

    static Object lockA = new Object();
    public void m1()
    {


        // 1,,收取前台传入的参数，记得合法性验证。。。

        //2

        //3

        //4
    }

    public synchronized void m2()
    {

    }

    public static void main(String[] args)
    {
        Thread thread = new Thread();
        thread.start();
        thread.start();

        // master 修改提交
    }
}