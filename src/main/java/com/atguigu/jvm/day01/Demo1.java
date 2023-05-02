package com.atguigu.jvm.day01;

public class Demo1 {


    public static void main(String[] args) {
        a();
    }

    private static void a() {
        System.out.println("hello a！");
        b();

    }

    private static void b() {
        System.out.println("hello b!");
        a();
    }
}