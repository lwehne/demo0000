package com.atguigu.jvm.day01;

public class Demo {
    public Demo(){
        super();
    }

    public static void main(String[] args) {
        //  由启动类加载的：java.lang包
        Object obj = new Object();
        //  由启动类加载的：java.lang包
        String s = new String();
        //  自定义的加载器
        Demo demo = new Demo();

        //  获取到加载器： Bootstrap ， 但是这个加载器实现 C++ 因此看到的是null
        System.out.println(obj.getClass().getClassLoader());
        //  获取到加载器： Bootstrap ， 但是这个加载器实现 C++ 因此看到的是null
        System.out.println(s.getClass().getClassLoader());
        //  获取到加载器App ,Ext ,Bootstrap 但是这个加载器实现 C++ 因此看到的是null
        System.out.println(demo.getClass().getClassLoader().getParent().getParent());
        //  获取到加载器App的父级加载器：Ext ，并不是 由Ext 加载加载的
        System.out.println(demo.getClass().getClassLoader().getParent());
        //  获取到App 加载器！
        System.out.println(demo.getClass().getClassLoader());
    }
}
