package com.atguigu.juc;

import java.lang.ref.PhantomReference;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

class Phone //资源类，Phone.java---》Phone.class---> Phone Class
{
    public static synchronized void sendEmail()
    {
        //暂停4秒钟线程
        try { TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("----sendEmail");
    }

    public synchronized void sendSMS()
    {
        System.out.println("----sendSMS");
    }

    public void sayHello()
    {
        System.out.println("---hello 0325");
    }
}

/**
 * @auther zzyy
 * @create 2020-03-26 11:41
 * 题目：谈谈你对多线程锁的理解
 *
 * 1 标准访问，请问先打印邮件还是短信
 * 2 邮件方法暂停4秒钟，请问先打印邮件还是短信
 * 3 新增普通sayHello方法，请问先打印邮件还是hello
 * 4 两部手机，请问先打印邮件还是短信
 * 5 两个静态同步方法，1部手机，请问先打印邮件还是短信
 * 6 两个静态同步方法，2部手机，请问先打印邮件还是短信
 * 7 1个静态同步邮件方法，1个普通同步短信方法,1部手机，请问先打印邮件还是短信
 * 8 1个静态同步邮件方法，1个普通同步短信方法,2部手机，请问先打印邮件还是短信
 *
 *  synchronized是实现同步的基础：Java中的每一个对象都可以作为锁。
 *  具体表现为以下3种形式。
作用于实例方法，当前实例加锁，进入同步代码前要获得当前实例的锁；
作用于静态方法，当前类加锁，进去同步代码前要获得当前类对象的锁；
作用于代码块，对括号里配置的对象加锁。



 *  1-2
 *  *  一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，
 *  *  其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法
 *  *  锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法
 *
 *   *  3-4
 *  *   *  加个普通方法后发现和同步锁无关
 *  *  *  换成两个对象后，不是同一把锁了，情况立刻变化。
 *
 *  5-6
 *  都换成静态同步方法后，情况又变化
 *  若是普通同步方法，new     this,   具体的一部部手机,所有的普通同步方法用的都是同一把锁——实例对象本身，
 *  若是静态同步方法，static  Class ，唯一的一个模板
 *
 *  7-8
 *   *  当一个线程试图访问同步代码时它首先必须得到锁，退出或抛出异常时必须释放锁。
 *  *
 *  *  所有的普通同步方法用的都是同一把锁——实例对象本身，，就是new出来的具体实例对象本身
 *  *  也就是说如果一个实例对象的普通同步方法获取锁后，该实例对象的其他普通同步方法必须等待获取锁的方法释放锁后才能获取锁，
 *  *  可是别的实例对象的普通同步方法因为跟该实例对象的普通同步方法用的是不同的锁，所以不用等待该实例对象已获取锁的普通
 *  *  同步方法释放锁就可以获取他们自己的锁。
 *  *
 *  *  所有的静态同步方法用的也是同一把锁——类对象本身，就是我们说过的唯一模板Class
 *  *  具体实例对象this和唯一模板Class，这两把锁是两个不同的对象，所以静态同步方法与普通同步方法之间是不会有竞态条件的。
 *  *  但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁 *
 */
public class Lock8Demo
{
    public static void main(String[] args) throws InterruptedException
    {
        Phone phone = new Phone();//this
        Phone phone2 = new Phone();

        new Thread(() -> {
            phone.sendEmail();
        },"t1").start();

        Thread.sleep(100);

        new Thread(() -> {
            //phone.sendSMS();
            //phone.sayHello();
            phone2.sendSMS();
        },"t2").start();

    }
}

































/***  * 笔记
 *
 *
 *  synchronized是实现同步的基础：Java中的每一个对象都可以作为锁。
 *  具体表现为以下3种形式。
    作用于实例方法，当前实例加锁，进入同步代码前要获得当前实例的锁；
    作用于静态方法，当前类加锁，进去同步代码前要获得当前类对象的锁；
    作用于代码块，对括号里配置的对象加锁。



 *  1-2
 *  *  一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，
 *  *  其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法
 *  *  锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法
 *  3-4
 *   *  加个普通方法后发现和同步锁无关
 *  *  换成两个对象后，不是同一把锁了，情况立刻变化。
 *  5-6
 *  都换成静态同步方法后，情况又变化
 *  若是普通同步方法，new     this,   具体的一部部手机,所有的普通同步方法用的都是同一把锁——实例对象本身，
 *  若是静态同步方法，static  Class ，唯一的一个模板
 *
 *  7-8
 *   *  当一个线程试图访问同步代码时它首先必须得到锁，退出或抛出异常时必须释放锁。
 *  *
 *  *  所有的普通同步方法用的都是同一把锁——实例对象本身，，就是new出来的具体实例对象本身
 *  *  也就是说如果一个实例对象的普通同步方法获取锁后，该实例对象的其他普通同步方法必须等待获取锁的方法释放锁后才能获取锁，
 *  *  可是别的实例对象的普通同步方法因为跟该实例对象的普通同步方法用的是不同的锁，所以不用等待该实例对象已获取锁的普通
 *  *  同步方法释放锁就可以获取他们自己的锁。
 *  *
 *  *  所有的静态同步方法用的也是同一把锁——类对象本身，就是我们说过的唯一模板Class
 *  *  具体实例对象this和唯一模板Class，这两把锁是两个不同的对象，所以静态同步方法与普通同步方法之间是不会有竞态条件的。
 *  *  但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁
 *  */