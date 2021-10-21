package ThreadStudy;


//线程池

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Numberount implements Runnable{
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }
}

class Numberount1 implements Runnable{
    @Override
    public void run() {
        for(int i=20;i<25;i++){
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }
}

public class ThreadPoolTest {
    public static void main(String[] args) {
        Numberount numberount=new Numberount();
        Numberount1 numberount1=new Numberount1();
        ExecutorService service =Executors.newFixedThreadPool(5);
        service.execute(numberount);
        service.execute(numberount);
        service.shutdown();  //只有这个写完才可以关闭程序运行
}
}
