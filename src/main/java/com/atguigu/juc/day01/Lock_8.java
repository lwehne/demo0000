package com.atguigu.juc.day01;


class Phone{

    //  发送短信
    public static  void sendMsg() throws InterruptedException {
        synchronized(Phone.class){
            //  this 表示当前对象；
            Thread.sleep(4000);
            System.out.println("sendMsg...短信");
        }

    }
    //  发送邮件
    public  static void sendEmail(){
        synchronized(Object.class){
            System.out.println("sendEmail...邮件");

        }
    }
    //  普通方法
    public void hello(){
        System.out.println("hello");
    }
}

class F {
    //  定义一个方法a;
    public synchronized void a(){
        System.out.println("start -- a");
        b();
        System.out.println("end -- a");
    }
    //  定义一个方法b;
    public synchronized void b(){
        System.out.println("start -- b");
        System.out.println("end -- b");
    }
}


public class Lock_8 {
    /**
    1. 标准访问，先打印短信还是邮件
            先短信 ，再邮件（调用顺序）

    2. 停4秒在短信方法内，先打印短信还是邮件
            先短信，再邮件
            说明他们使用的是同一把锁！
    3. 普通的hello方法，是先打短信还是hello
            先hello，再短信！
            hello没上锁，和短信一起，短信等了4秒。
    4. 现在有两部手机，先打印短信还是邮件
            先邮件，再短信！
            说明他们使用的不是同一把锁！
            一起进行，短信睡4秒，各走各的，邮件先出。↓ 同一把锁需要排队，不同锁不需要排队 同步进行。
            调用方法的时候，不是同一个对象，根据结论2只要是同一个对象就是同一把锁！
            研究锁:
                通过同步代码块研究：
                只要是同一个对象就是同一把锁，这个锁是this!

    5. 两个静态同步方法，1部手机，先打印短信还是邮件
            先短信，再邮件
            说明是同一把锁！
    6. 两个静态同步方法，2部手机，先打印短信还是邮件
            先短信，再邮件
            说明是同一把锁！

            这个锁是谁：Phone.class ; 为什么不是this了?  随着类的加载而加载！ 类加载完成之后，才会有对象！this!
    7. 1个静态同步方法，1个普通同步方法，1部手机，先打印短信还是邮件
            先邮件，再短信
            说明不是同一把锁！
    8. 1个静态同步方法，1个普通同步方法，2部手机，先打印短信还是邮件
            先邮件，再短信
            说明不是同一把锁！
     */
    public static void main(String[] args) {
        //  资源类 new F()===> 匿名对象
        new F().a();
    //        Phone phone = new Phone();
    //        Phone phone2 = new Phone();
    //
    //        //  创建线程 短信
    //        new Thread(()->{
    //            try {
    //                phone.sendMsg();
    //            } catch (InterruptedException e) {
    //                throw new RuntimeException(e);
    //            }
    //        }).start();
    //        //  创建线程 邮件
    //        new Thread(()->{
    //            phone2.sendEmail();
    //            //  phone.hello();
    //        }).start();


    }
}
