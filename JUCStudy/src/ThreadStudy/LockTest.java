package ThreadStudy;

import java.util.concurrent.locks.ReentrantLock;

//lock 解决同步安全问题
class Window implements Runnable{
    private int ticket=100;
     //lock实现类
    private ReentrantLock lock=new ReentrantLock();
    @Override
    public void run() {
        while (true){
            try{
                lock.lock();   //操作前加锁，手动加锁解锁
                if(ticket>0){
                    System.out.println(Thread.currentThread().getName()+"卖出第"+ticket+"张票");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ticket--;
                }else
                    break;
            }finally {
                lock.unlock();   //执行完毕必定解锁
            }
        }
    }
}

public class LockTest {
    public static void main(String[] args) {
        Window w=new Window();
        Thread t1=new Thread(w);
        Thread t2=new Thread(w);
        Thread t3=new Thread(w);

        t1.start();
        t2.start();
        t3.start();
    }
}
