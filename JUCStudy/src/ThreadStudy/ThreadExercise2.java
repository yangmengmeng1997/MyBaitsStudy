package ThreadStudy;
//面向对象实现更加规范
//账户
class Account{
    private double balance;

    public Account(double balance){
        this.balance=balance;
    }
    //存钱这个方法操作了共享数据，所以是危险的，要加限制，注意此时的锁是account对象，这是唯一的
    public synchronized void addBalance(double add){
        balance=balance+add;
        System.out.println(Thread.currentThread().getName()+"存钱成功，当前余额为"+getBalance());
    }
    //获取余额也要加以限制，在存钱里加锁了也就安全了,在外面调用还是会线程错乱不加锁的话
    public double getBalance(){
        return balance;
    }
}
//客户 ，使用继承来实现并发
class Customer extends Thread{
    //保证构造时共用一个账户
    private Account account;
    public Customer(Account account){
        this.account=account;
    }

    @Override
    public synchronized void run() {
        for(int i=0;i<3;i++){
            account.addBalance(1000);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadExercise2 {
    public static void main(String[] args) {
        Account account=new Account(0);
        Customer c1=new Customer(account);
        Customer c2=new Customer(account);
        c1.start();
        c2.start();
    }
}
