package com.atguigu.juc.day02;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        //
        //  CyclicBarrier(3, ()->{ "恭喜闯关成功" })
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,()->{
            System.out.println(Thread.currentThread().getName() + "过关了");
        });
        //  打游戏：
        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                //  打游戏组队过关.
                try {
                    //  第一关：
                    System.out.println(Thread.currentThread().getName() + " 开始第一关");
                    TimeUnit.SECONDS.sleep(new Random().nextInt(4));
                    System.out.println(Thread.currentThread().getName() + " 开始打boss");
                    cyclicBarrier.await();

                    //  第二关：
                    System.out.println(Thread.currentThread().getName() + " 开始第2关");
                    TimeUnit.SECONDS.sleep(new Random().nextInt(4));
                    System.out.println(Thread.currentThread().getName() + " 开始打boss");
                    cyclicBarrier.await();
                    //  第三关：
                    System.out.println(Thread.currentThread().getName() + " 开始第3关");
                    TimeUnit.SECONDS.sleep(new Random().nextInt(4));
                    System.out.println(Thread.currentThread().getName() + " 开始打boss");
                    cyclicBarrier.await();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            },String.valueOf(i)).start();
        }

    }
}
