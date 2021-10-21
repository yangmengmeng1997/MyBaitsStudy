package ThreadStudy;


//解决多线程并发问题，加锁，加入的是同步代码块
//解决实现问题
class Window3 implements Runnable{
    private int ticket=100;
    @Override
    public void run() {
        while (true){
            synchronized (this){
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
            }
        }

    }
}


public class WindowTest3 {
    public static void main(String[] args) {
        Window3 w=new Window3();
        Thread t1=new Thread(w);
        Thread t2=new Thread(w);
        Thread t3=new Thread(w);
        t1.start();
        t2.start();
        t3.start();
    }

}
