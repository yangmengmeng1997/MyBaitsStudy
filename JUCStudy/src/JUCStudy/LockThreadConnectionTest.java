package JUCStudy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Share{
    private int num=0;
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    public void add(){
        lock.lock();
        try {
            //1.判断
            while (num!=0){
                condition.await();
            }  //while 判断防止虚假唤醒问题
            //2.干活
            num++;
            System.out.println("add方法执行，num的值为:"+num);
            Thread.sleep(1000);
            //3.通知
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void del() {
        lock.lock();
        try {
            while (num!=1){
                condition.await();
            }
            num--;
            System.out.println("del方法执行，num的值为:"+num);
            Thread.sleep(1000);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


public class LockThreadConnectionTest {
    public static void main(String[] args) {
        Share share=new Share();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    share.add();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    share.del();
                }
            }
        }).start();

    }
}
