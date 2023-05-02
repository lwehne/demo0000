package com.atguigu.juc.day01;

@FunctionalInterface //函数式接口
interface Foo{
    void sayHi();

    //  其他方法；默认方法，静态方法，
    default int add(int x,int y){
        return x+y;
    }

    static int div(int a,int b){
        return a/b;
    }

}
public class LambdaDemo {
    public static void main(String[] args) {
        //  拉姆达表达式口诀：复制小括号，写死右箭头，落地大括号！
        Foo foo = ()->{
            System.out.println("hello 很好");
        };
        foo.sayHi();

        System.out.println(foo.add(3, 3));
        //  foo.div(); 为什么! 静态static :1. 随着类的加载而加载;
        System.out.println(Foo.div(6, 2));

    }
}
