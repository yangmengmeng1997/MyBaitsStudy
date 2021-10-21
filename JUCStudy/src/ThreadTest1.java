

class Mywindow extends Thread{
    @Override
    public void run() {
        for(int i=0;i<20;i++){
            System.out.println("当前线程名字为"+Thread.currentThread().getName());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


public class ThreadTest1 {
    public static void main(String[] args) {
        Mywindow m1=new Mywindow();
        Mywindow m2=new Mywindow();
        Mywindow m3=new Mywindow();
        m1.start();
        m2.start();
        m3.start();
    }
}
