package com.atguigu.juc.day03;

public class VolatileOrderDemo {

    static int a,b;
    static int x,y;

    public static void main(String[] args) throws InterruptedException {

        int i = 0;
        while (true){
            i++;
            a = b = x = y = 0;
            Thread thread1 = new Thread(() -> {
                a = 1;
                x = b;
            }, "");	
            Thread thread2 = new Thread(() -> {
                b = 1;
                y = a;
            }, "");

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            System.out.println("第" + i + "次打印：x=" + x + ", y=" + y);

            if (x == 0 && y == 0){
                break;
            }
        }

        /**
         结果：
         第1次打印：x=1, y=1
         第2次打印：x=0, y=1
         ...
         第1672次打印：x=0, y=1
         第1673次打印：x=1, y=0
         ...
         第1738次打印：x=0, y=0
         */
    }
}