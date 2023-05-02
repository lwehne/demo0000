package com.atguigu;

public class Test {
    public static void main(String[] args) {
//  （1）1.创建一个Customer ，名字叫 Jane Smith；66346
//  （1）1.创建一个Customer ，名字叫 Jane Smith；66345
        Customer customer = new Customer("Smith", "Jane");
//      2.他有一个账号为1000，余额为2000元，年利率为 1.23％ 的账户。
        Account account = new Account(1000, 2000, 0.0123);
        
//   （2）	对Jane Smith操作。
        //1.存入 100 元，再取出960元。再取出2000元。
        customer.setAccount(account);
        customer.getAccount().deposit(100);
        customer.getAccount().withdraw(960);
        //2.打印出Jane Smith 的基本信息
        System.out.println("Customer [ " + customer.getFirstName() + "，" + customer.getLastName() + " ] " + "has a account: id is " + account.getId() + "," +
                " annualInterestRate is " + account.getAnnualInterestRate() * 100 + "%" + "，balance is " + account.getBalance());
    }
}