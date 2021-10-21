package ThreadStudy;

//解决实现问题
class Window2 implements Runnable{
    private int ticket=100;
    @Override
    public void run() {
        while (true) {
            //不加判断就会发生死循环
            if(ticket>0)
               show();
            else
                break;
        }
    }

    //负责执行卖票，也就是说负责操作共享数据的，申明为同步方法
    public  synchronized void show(){
        if(ticket>0){
            System.out.println(Thread.currentThread().getName()+"卖出第"+ticket+"张票");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ticket--;
        }
    }
}

public class WindowTest2 {
    public static void main(String[] args) {
        Window2 w=new Window2();
        Thread t1=new Thread(w);
        Thread t2=new Thread(w);
        Thread t3=new Thread(w);
        t1.start();
        t2.start();
        t3.start();
    }
}
