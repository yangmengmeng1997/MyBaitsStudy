package ThreadStudy;

import java.util.concurrent.locks.ReentrantLock;

class Account2{
    private double balance;
    private ReentrantLock lock=new ReentrantLock();

    public Account2(double balance){
        this.balance=balance;
    }
    //存钱这个方法操作了共享数据，所以是危险的，要加限制，注意此时的锁是account对象，这是唯一的
    public  void addBalance(double add){
        lock.lock();
        balance=balance+add;
        System.out.println(Thread.currentThread().getName()+"存钱成功，当前余额为"+getBalance());
        lock.unlock();
    }
    //获取余额也要加以限制，在存钱里加锁了也就安全了,在外面调用还是会线程错乱不加锁的话
    public double getBalance(){
        return balance;
    }
}
//客户 ，使用继承来实现并发
class Customer2 extends Thread {
    //保证构造时共用一个账户
    private Account2 account;

    public Customer2(Account2 account) {
        this.account = account;
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < 3; i++) {
            account.addBalance(1000);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadTest3 {
    public static void main(String[] args) {
        Account2 account2=new Account2(0);
        Customer2 c1=new Customer2(account2);
        Customer2 c2=new Customer2(account2);
        c1.start();
        c2.start();
    }

}
