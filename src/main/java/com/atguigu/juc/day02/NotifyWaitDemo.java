package com.atguigu.juc.day02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//  资源类
class ShareDataOne{
    //    两个线程操作一个初始值为0的变量，实现一个线程对变量增加1，一个线程对变量减少1，交替10轮。
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    //  synchronized --LOCK;
    //  定义一个+1; synchronized ,wait,notify notifyAll; || lock;--->Condition; await signal signalAll
    public void increment(){
        lock.lock();
        try {
            //  判断 number != 0 ; 等待
            while (number!=0){
                try {
                    //  this.wait();
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //  干活 如果number = 0 ; +1;
            number++;
            System.out.println(Thread.currentThread().getName() + ": " + number);
            //  通知 唤醒其他线程
            //  this.notifyAll(); // 表示唤醒所有线程
            //  this.notify();  //  唤醒某一个线程
            //  this.condition.signal();
            this.condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
    //  定义一个-1;
    public void decrement(){
        lock.lock();
        try {
            //  判断 number != 1 ; 等待
            //  if-->while
            while (number!=1){
                try {
                    //  this.wait();
                    this.condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //  干活
            //  如果 number = 1 ; -1;
            number--;
            System.out.println(Thread.currentThread().getName() + ": " + number);
            //  通知
            //  this.notifyAll(); // 表示唤醒所有线程
            //  this.notify();  //  唤醒某一个线程
            this.condition.signalAll();
        } finally {
            lock.unlock();
        }

    }



}
public class NotifyWaitDemo {

    public static void main(String[] args) {
        //  线程操作资源类
        ShareDataOne shareDataOne = new ShareDataOne();
        //   两个线程操作一个初始值为0的变量，实现一个线程对变量增加1，一个线程对变量减少1，交替10轮。
        //  创建线程 +1
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareDataOne.increment();
            }
        },"AA").start();

        //  创建线程 -1
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareDataOne.decrement();
            }
        },"BB").start();

        //  创建线程 +1
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareDataOne.increment();
            }
        },"CC").start();

        //  创建线程 -1
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareDataOne.decrement();
            }
        },"DD").start();
    }

}
