package com.atguigu.juc;

@FunctionalInterface
interface Foo
{
    public int add(int x,int y);

    default int div(int x,int y)
    {
        return x/y;
    }

    static int sub(int x ,int y)
    {
        return x - y;
    }
}

/**
 * @auther zzyy
 * @create 2020-03-26 11:40
 *lambda Express
 * 接口里面有且仅有一个方法，函数式接口前提下：
 *
 * 1 拷贝小括号，写死右箭头，落地大括号
 * 2 @FunctionalInterface
 * 3 default方法
 * 4 静态方法
 */
public class LambdaExpressDemo
{
    public static void main(String[] args)
    {
        /*Foo foo = new Foo()
        {
            @Override
            public void sayHello()
            {

            }
        }*/

        /*Foo foo = () -> System.out.println("----java210325 Lambda express");
        foo.sayHello();*/

        // public int add(int x,int y);

        Foo foo = (int x,int y) -> {
            System.out.println("------come in");
            int result = x + y;
            return result;
        };
        System.out.println(foo.add(3, 5));

        System.out.println(foo.div(10, 2));

        System.out.println(Foo.sub(5, 3));

    }
}
