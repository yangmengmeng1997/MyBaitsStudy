package ThreadStudy;

import java.util.Collection;

//实现多用户的存钱问题，可以交互
public class ThreadExercise {
    public static void main(String[] args) {
        Count count=new Count();
        Thread t1=new Thread(count);
        Thread t2=new Thread(count);
        t1.setName("c1");
        t2.setName("c2");
        t1.start();
        t2.start();
    }
}
class Count implements Runnable{
    private int mycount=0;
    @Override
    public void run() {
            for(int i=0;i<3;i++){
                synchronized (this) {   //包在里面才可以呀
                    mycount = mycount + 1000;
                    System.out.println(Thread.currentThread().getName() + "第"+ i + "次存款1000元" + "当前余额为" + mycount);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}
