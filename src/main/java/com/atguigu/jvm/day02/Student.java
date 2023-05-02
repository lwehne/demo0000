package com.atguigu.jvm.day02;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

class F extends PhantomReference{

    /**
     * Creates a new phantom reference that refers to the given object and
     * is registered with the given queue.
     *
     * <p> It is possible to create a phantom reference with a <tt>null</tt>
     * queue, but such a reference is completely useless: Its <tt>get</tt>
     * method will always return null and, since it does not have a queue, it
     * will never be enqueued.
     *
     * @param referent the object the new phantom reference will refer to
     * @param q        the queue with which the reference is to be registered,
     *                 or <tt>null</tt> if registration is not required
     */
    public F(Object referent, ReferenceQueue q) {
        super(referent, q);
    }
}
public class Student {


    //  static Student student;
    //  我是构造函数
    public Student(){

        System.out.println("我是构造函数");
    }
    //  finalize() -- Object

    //  这个对象要被回收之前一定会执行这个方法.

    @Override
    protected void finalize() throws Throwable {
        // F f = new F();
        System.out.println("我是finalize....");
        // student = new Student();
        super.finalize();
    }

    //  @Override
    //    protected void finalize() throws Throwable {
    //        System.out.println("我是finalize....");
    //        //  有可能会活！
    //        //  student = new Student();
    //        //  super.finalize();
    //    }

    //  主入口：
    public static void main(String[] args) {
        //  可以作为GC Roots; 普通对象
        Student student = new Student();
        student = null;
        //  調用gc 方法 如果立刻调用 肯定会执行finalize 方法！ 如果没有立刻执行,不会调用.
        System.gc();
        System.out.println("---- main ---- 結束");
    }
    //  如果 System.gc(); 被调用，立即回收的话：我是构造函数  我是finalize.... ---- main ---- 結束
    //  如果 System.gc(); 被调用，不会立即执行；我是构造函数   ---- main ---- 結束    我是finalize....
}