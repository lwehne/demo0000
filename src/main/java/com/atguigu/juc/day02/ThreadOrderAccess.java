package com.atguigu.juc.day02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//  线程操作资源类:
class ShareDataTwo{
    /*
        1. 有一个锁Lock，3把钥匙Condition
        2. 有顺序通知（切换线程），需要有标识位
        3. 判断标志位
        4. 输出线程名 + 内容
        5. 修改标识符，通知下一个

        AA打印5次，BB打印10次，CC打印15次  打印10轮
     */
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition(); // AA
    private Condition c2 = lock.newCondition(); // BB
    private Condition c3 = lock.newCondition(); // CC

    //  创建标识位
    private int flag = 1;   // 1=AA 2=BB 3=CC
    //  AA打印：
    public void print5(){
        //  上锁
        lock.lock();
        try {
            //  判断
            while (flag!=1){
                try {
                    c1.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //  干活！
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+ "i:\t"+i);
            }
            //  通知：BB 线程执行！
            flag=2;
            c2.signal();
        } finally {
            //  解锁
            lock.unlock();
        }

    }
    //  BB打印：
    public void print10(){
        //  上锁
        lock.lock();
        try {
            //  判断
            while (flag!=2){
                try {
                    c2.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //  干活！
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+ "i:\t"+i);
            }
            //  通知：CC 线程执行！
            flag=3;
            c3.signal();
        } finally {
            //  解锁
            lock.unlock();
        }

    }
    //  CC打印：
    public void print15(){
        //  上锁
        lock.lock();
        try {
            //  判断
            while (flag!=3){
                try {
                    c3.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //  干活！
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName()+ "i:\t"+i);
            }
            //  通知：BB 线程执行！
            flag=1;
            c1.signal();
        } finally {
            //  解锁
            lock.unlock();
        }
    }
}

public class ThreadOrderAccess {

    //  主入口:
    public static void main(String[] args) {
        //  线程操作资源类
        //  判断干活通知
        //  防止虚假唤醒.
        ShareDataTwo shareDataTwo = new ShareDataTwo();
        //  创建线程！
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareDataTwo.print5();
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareDataTwo.print10();
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareDataTwo.print15();
            }
        },"CC").start();

    }
}
