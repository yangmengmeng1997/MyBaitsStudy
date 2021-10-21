package ThreadStudy;

public class ThreadComunicate {
    public static void main(String[] args) {
        Number number=new Number();
        Thread t1=new Thread(number);
        Thread t2=new Thread(number);
        t1.start();
        t2.start();
    }
}
class Number implements Runnable{
    private int number=1;
    @Override
    public void run() {
        while (true){
            synchronized(this){
                //必须在同步代码块中使用这些方法
                notify();    //将被阻塞的进程唤醒，没有就不用唤醒，实现交替打印
                if(number<=100){
                    System.out.println(Thread.currentThread().getName()+":"+number);
                    number++;
                    try {
                        wait();   //使得当前进程实现阻塞并且释放锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else
                    break;
            }
        }
    }
}