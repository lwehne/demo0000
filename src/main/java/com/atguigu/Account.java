package com.atguigu;

//账户类
public class Account {
    private int id;//账号id
    private double balance;//余额
    private double annualInterestRate; //年利率
 
    //取款方法   amount == 金额
    public void withdraw(double amount) {
        if (this.balance < amount) {
            System.out.println("抱歉，余额不足!");
            return;
        }
        this.balance -= amount;
        System.out.println("成功取出: " + amount + "\t" + "目前余额为:" + balance);
    }
 
    //存款方法
    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("成功存入: " + amount + "\t" + "目前余额为:" + balance);
    }
 
    public Account() {
    }
 
    public Account(int id, double balance, double annualInterestRate) {
        this.id = id;
        this.balance = balance;
        this.annualInterestRate = annualInterestRate;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public double getBalance() {
        return balance;
    }
 
    public void setBalance(double balance) {
        this.balance = balance;
    }
 
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }
 
    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }
}