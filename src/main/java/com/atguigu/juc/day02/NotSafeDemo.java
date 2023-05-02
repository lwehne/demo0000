package com.atguigu.juc.day02;

import com.atguigu.jvm.day02.Student;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class NotSafeDemo {

    //  说明集合在多线程的情况下是不安全的！
    public static void main(String[] args) {
        //  创建集合
        //  demoSEt();
        //  HashMap<String, String> hashMap = new HashMap<>();
        //  Map<Object, Object> hashMap = Collections.synchronizedMap(new HashMap<>());
          ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();
        //  创建线程
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                hashMap.put(UUID.randomUUID().toString().substring(3),UUID.randomUUID().toString().substring(3));
                System.out.println(hashMap);
            }).start();
        }
    }

    private static void demoSEt() {
        //  listDemo();
        //  HashSet<String> set = new HashSet<>();
        //  Set<Object> set = Collections.synchronizedSet(new HashSet<>());
        CopyOnWriteArraySet<Object> set = new CopyOnWriteArraySet<>(new HashSet<>());
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(5));
                System.out.println(set);
            }).start();
        }
    }

    private static void listDemo() {
        //        ArrayList<String> list = new ArrayList<>();
//        Vector<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                //  添加数据；
                list.add(UUID.randomUUID().toString().substring(3));
                //  打印数据：
                System.out.println(list);
            }).start();
        }
    }
}
