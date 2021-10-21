package ThreadStudy;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class NewThread implements Callable {
    @Override
    public Object call() throws Exception {
          int sum=0;
          for(int i=0;i<10;i++){
              System.out.println(i);
              sum+=i;
          }
          return sum;
    }
}

//创建线程的两种方式
public class ThreadNew {
    //类比于Runnable
    public static void main(String[] args) {
        NewThread newThread=new NewThread();
        FutureTask futureTask = new FutureTask(newThread);
        new Thread(futureTask).start();   //建立线程类，创建futureTask方法，还是要调用Thread方法
        try {
            Object obj=futureTask.get();   //调用返回值
            System.out.println(obj);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
