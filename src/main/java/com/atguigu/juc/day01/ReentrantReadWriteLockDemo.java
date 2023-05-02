package com.atguigu.juc.day01;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//  模拟从缓存中获取数据.
class MyCache{
    //  暂时使用map 来代替缓存. volatile -- 关键词：被它所修饰的变量，能被多个线程所共享！
    private volatile HashMap<String,String> hashMap = new HashMap<>();

    //  声明一个读写锁
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    /**
     * 写入数据方法。
     * @param key
     * @param value
     */
    public void put(String key,String value){
        //  调用写锁 上锁！
        rwl.writeLock().lock();
        rwl.writeLock().newCondition();
        //  执行业务逻辑
        try {
            System.out.println(Thread.currentThread().getName() + " 开始写入！");
            try {
                //  睡眠
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //  写入数据。
            hashMap.put(key,value);
            System.out.println(Thread.currentThread().getName() + " 写入完成！");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            //  解锁
            rwl.writeLock().unlock();
        }
    }

    public void get(String key){
        //  获取读锁
        rwl.readLock().lock();
        rwl.readLock().newCondition();
        try {
            System.out.println(Thread.currentThread().getName() + " 开始读出！");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //  本质
            String str = hashMap.get(key);
            System.out.println("读取到的数据：\t"+str);

            System.out.println(Thread.currentThread().getName() + " 读取完成！");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            rwl.readLock().unlock();
        }
    }

    //  读写锁：
    public void test(){
        rwl.writeLock().lock();
        System.out.println("获取到写锁。。。。");
        rwl.readLock().lock();
        System.out.println("获取到读锁----------");
        rwl.writeLock().unlock();
        System.out.println("释放写锁==============");
        rwl.readLock().unlock();
        System.out.println("释放读锁++++++++++++++++");
    }
}

class CachedData {
    Object data;
    volatile boolean cacheValid;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    //  写锁--读锁---释放写锁的过程. 锁降级！
    void processCachedData() {
        //  上读锁
        rwl.readLock().lock();
        //  cacheValid 默认false!
        if (!cacheValid) {
            // Must release read lock before acquiring write lock
            //  释放读锁
            rwl.readLock().unlock();
            //  获取写锁！
            rwl.writeLock().lock();
            try {
                // Recheck state because another thread might have
                // acquired write lock and changed state before we did.
                if (!cacheValid) {
                    //  表示从数据库或缓存中获取数据.
                    data = "atguigu";
                    cacheValid = true;
                }
                // Downgrade by acquiring read lock before releasing write lock
                //  上读锁！
                rwl.readLock().lock();
            } finally {
                //  释写锁.
                rwl.writeLock().unlock(); // Unlock write, still hold read
            }
        }

        try {
            use(data);
        } finally {
            rwl.readLock().unlock();
        }
    }

    private void use(Object data) {
        System.out.println(data);
    }
}

public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        //  线程操作资源类：
        MyCache myCache = new MyCache();
        // myCache.test();
        //  创建线程读取，写入
        for (int i = 0; i < 5; i++) {
            //  定义一个key，value;
            String num = i+"";
            //  创建5个线程
            new Thread(()->{
                //  写入数据；
                myCache.put(num,num);
            },String.valueOf(i)).start();
        }

        //  5个读取线程。
        for (int i = 0; i < 5; i++) {
            //  定义一个key，value;
            String num = i+"";
            new Thread(()->{
                myCache.get(num);
            },String.valueOf(i)).start();
        }
    }
}
