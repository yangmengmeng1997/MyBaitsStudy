package ThreadStudy;

//解决继承问题
class Window1 extends Thread{
    private static int ticket=100;
    @Override
    public void run() {
        while (true){
                if(ticket>0){
                    show();
                }else
                    break;
            }
        }
        //这里就是锁的问题，默认是this对象，那么这里就有三个锁对象
        //声明为类方法就可以，此时的锁对象就是当前类，当前类是唯一的就是Window1.class
        public static synchronized void show(){
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


public class WindowTest1 {
    public static void main(String[] args) {
        Window1 w1=new Window1();
        Window1 w2=new Window1();
        Window1 w3=new Window1();
        w1.start();
        w2.start();
        w3.start();
    }
}
