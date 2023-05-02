package com.atguigu.juc.day02;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

    public static void main(String[] args) {
        //  6辆车抢占3个车位
        Semaphore semaphore = new Semaphore(3);

        //  创建线程：
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                try {
                    //  抢占车位.
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 抢到了一个停车位！！");
                    // 停一会儿车  new Random().nextInt(10) = 获取10以内的随机数.
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                    System.out.println(Thread.currentThread().getName() + " 离开停车位！！");
                    // 开走，释放一个停车位
                    semaphore.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            },String.valueOf(i)).start();
        }
    }
}
