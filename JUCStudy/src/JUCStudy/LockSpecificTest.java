package JUCStudy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//实现各个进程按照要求执行若干次
class ShareResource{
    //定义标志位 约定1代表A线程，2代表B线程，3代表C线程
    private int flag=1;
    private Lock lock=new ReentrantLock();
    //创建三个condition来对应每一个线程？
    private Condition condition1=lock.newCondition();
    private Condition condition2=lock.newCondition();
    private Condition condition3=lock.newCondition();

    //方法1，输出你好
    public void m1(){
        lock.lock();
        try {
            //判断
            while (flag!=1){
                condition1.await();
            }
            //干活
            System.out.println("你好");
            flag=2;
            Thread.sleep(1000);
            //通知
            condition2.signal();   //通知要用多个condition通知，不然会死锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //方法2，输出你好
    public void m2(){
        lock.lock();
        try {
            while (flag!=2){
                condition2.await();
            }
            System.out.println("你也好");
            flag=3;
            Thread.sleep(1000);
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //方法3，输出你好
    public void m3(){
        lock.lock();
        try {
            while (flag!=3){
                condition3.await();
            }
            System.out.println("你好我好大家好");
            flag=1;
            Thread.sleep(1000);
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class LockSpecificTest {
    public static void main(String[] args) {
        ShareResource shareResource=new ShareResource();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    shareResource.m1();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    shareResource.m2();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    shareResource.m3();
                }
            }
        }).start();
    }

}
