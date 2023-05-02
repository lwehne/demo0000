package com.atguigu.juc.day01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//  run(); start();--> 执行线程，让线程独立！ 默认调用run();
class A extends Thread{
    @Override
    public void run() {
        System.out.println("Thread....");
        super.run();
    }
}
class B implements Runnable{

    @Override
    public void run() {
        System.out.println("Runnable...");
    }
}

class C implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"Callable接口...");
        return 1024;
    }
}
public class Demo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //  callable 创建线程!
        //  因为Thread() 构造函数中不能直接传递Callable接口;
        //  Thread(Runnable target)
        //  Runnable 接口 有个实现类 FutureTask
        //  FutureTask(Callable<V> callable)
        FutureTask<Integer> futureTask = new FutureTask<>(new C());
        FutureTask<Integer> futureTask1 = new FutureTask<>(new C());

        //  Thread(new FutureTask<>(new Callable()));
        //  Thread(Runnable target, String name)
        //  new Thread(futureTask).start();
        //  Thread(new FutureTask<>(new Callable()),"AA");
        //  new Thread(futureTask,"AA").start();
        FutureTask<Integer> integerFutureTask = new FutureTask<>(() -> {
            System.out.println("2048");
            return 2048;
        });
        new Thread(integerFutureTask,"CC").start();

        System.out.println(integerFutureTask.get());

        //  new Thread(futureTask1,"BB").start();

        //  boolean flag = futureTask.isDone();
        //        while (!futureTask.isDone()){
        //            System.out.println("wait......");
        //        }
        //        //  如何获取到返回值：
        //        try {
        //            System.out.println(futureTask.get());
        ////            System.out.println(futureTask1.get());
        //        } catch (InterruptedException e) {
        //            throw new RuntimeException(e);
        //        } catch (ExecutionException e) {
        //            throw new RuntimeException(e);
        //        }


        //  Thread()
        //        A a = new A();
        //        //  Thread(Runnable target)
        //        Thread b = new Thread(new B());
        //
        ////        a.start();
        ////        b.start();
        //        a.run();
        //        b.run();
        //  Thread(Runnable target)
        //        @FunctionalInterface
        //        public interface Runnable {
        //            /**
        //             * When an object implementing interface <code>Runnable</code> is used
        //             * to create a thread, starting the thread causes the object's
        //             * <code>run</code> method to be called in that separately executing
        //             * thread.
        //             * <p>
        //             * The general contract of the method <code>run</code> is that it may
        //             * take any action whatsoever.
        //             *
        //             * @see     java.lang.Thread#run()
        //             */
        //            public abstract void run();
        //        }

        //        new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                System.out.println("匿名内部类...");
        //            }
        //        }).start();
        //        //  创建线程：使用拉姆达表达式：前提{必须是函数式接口 -- @FunctionalInterface }
        //        //  拉姆达表达式口诀：复制小括号，写死右箭头，落地大括号！
        //        //  箭头右边：Runnable的run(); 方法的实现
        //        //  箭头左边：Runnable的run(); 的参数
        //
        //        //  Thread(Runnable target)
        //        new Thread(()->{
        //            System.out.println("拉姆达表达式");}
        //        ).start();
        //        //  Thread(Runnable target, String name)
        //        new Thread(()->{
        //            System.out.println(Thread.currentThread().getName()+"lambda");
        //        },"A").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "lambda表达式");
        },"线程名字").start();


    }
}
