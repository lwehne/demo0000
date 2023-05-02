package com.atguigu.jvm.day02;

public class Demo3 {

    public static void main(String[] args) {
        System.out.println("堆的最大值，默认是内存的1/4");
        System.out.println(Runtime.getRuntime().maxMemory() / 1024.0 / 1024 + "M");
        System.out.println("堆的当前总大小，默认是内存的1/64");
        System.out.println(Runtime.getRuntime().totalMemory() / 1024.0 / 1024 + "M");
        System.out.println("==================================================");

        //        byte[] b = null;
        //        for (int i = 0; i < 10; i++) {
        //            b = new byte[1 * 1024 * 1024];
        //        }
    }
}
