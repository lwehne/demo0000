package com.atguigu.juc.day01;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

//  资源类：
class Ticket{
    //  定义一个票数
    int number = 30;
    //  定义一个买票方法。
    /*
        // 1.同步代码块
        synchronized (){
        }
        //  2.同步方法
        public synchronized void sale()
    */
    //  lock 接口的实现类：
    //  公平锁：
//    private final ReentrantLock lock = new ReentrantLock(true);
    private final ReentrantLock lock = new ReentrantLock();

    public void sale(){

        //  普通方法
        //  lock.lock();
        try {
            boolean result = lock.tryLock(5, TimeUnit.SECONDS);
            //  判断
            if (result){
                try {
                    System.out.println("获取到锁");
                    //  执行卖票的业务逻辑
                    //  判断当前资源的数量.
                    if (number<=0){
                        System.out.println("车票已售罄");
                        return;
                    }
                    //  当前票数; 还剩多少；
                    System.out.println(Thread.currentThread().getName() + "开始买票，当前票数：" + number);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "买票结束，剩余票数：" + --number);
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }finally {
                    //  释放锁资源
                    lock.unlock();
                }
            }else {
                System.out.println("没有获取到锁");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //        try {
        //            //  判断当前资源的数量.
        //            if (number<=0){
        //                System.out.println("车票已售罄");
        //                return;
        //            }
        //            //  当前票数; 还剩多少；
        //            System.out.println(Thread.currentThread().getName() + "开始买票，当前票数：" + number);
        //            try {
        //                Thread.sleep(200);
        //            } catch (InterruptedException e) {
        //                throw new RuntimeException(e);
        //            }
        //            System.out.println(Thread.currentThread().getName() + "买票结束，剩余票数：" + --number);
        //            //  检查票数：
        //            // this.check();
        //
        //        } catch (RuntimeException e) {
        //            throw new RuntimeException(e);
        //        } finally {
        //            //  释放资源的代码.
        //            lock.unlock();
        //        }

    }

    //   check方法测试锁的可重入性
    private void check() {
        lock.lock();
        System.out.println("-----------检查锁--------");
        lock.unlock();
    }

}

public class SaleTicket {
    public static void main(String[] args) {
        //  线程操作资源类
        Ticket ticket = new Ticket();
        //  创建线程
        new Thread(()->{
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }

        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }
        },"C").start();
    }
}
