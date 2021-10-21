package JUCStudy;

//死锁的简单例子
public class DeadLockDemo {
    public static void main(String[] args) {
        Object a=new Object();
        Object b=new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a){
                    System.out.println("获得锁A,期望获得B");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (b){
                        System.out.println("获得了锁B");
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (b){
                    System.out.println("获得锁B,期望获得A");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (a){
                        System.out.println("获得了锁A");
                    }
                }
            }
        }).start();

    }
}
