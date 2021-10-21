package ThreadStudy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Clerk2{
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    private int productCount=0;

    //使用lock锁机制来改写
    public void ProduceProduct() {
        lock.lock();
        try {
            if(productCount<20){
                productCount++;
                System.out.println(Thread.currentThread().getName()+"生产第"+productCount+"产品");
                condition.signal();
                Thread.sleep(1000);
            }else{
                    System.out.println("生产池已满，等待消费。。。");
                    condition.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    //定义生产产品
    public void ConsumerProduct(){
        lock.lock();
        try {
            if(productCount>0){
                productCount--;
                System.out.println(Thread.currentThread().getName()+"消费第"+productCount+"产品");
                condition.signal();
                Thread.sleep(1000);
            }else{
                    System.out.println("生产池已空，等待生产。。。");
                    condition.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}


//生产者,定义私有共用一个
class Producer2 extends Thread{
    private Clerk2 clerk;
    public Producer2(Clerk2 c){
        this.clerk=c;
    }

    @Override
    public void run() {
        while (true){
            clerk.ProduceProduct();
        }
    }
}

//消费者 定义私有共用一个
class Consumer2 extends Thread{
    private Clerk2 clerk;
    public Consumer2(Clerk2 c){
        this.clerk=c;
    }

    @Override
    public void run() {
        while (true){
            //Run方法写简单逻辑，具体的到类里面实现具体的方法
            clerk.ConsumerProduct();
        }
    }
}



public class ProduceAndConsumer2 {
    public static void main(String[] args) {
        Clerk2 clerk2=new Clerk2();
        Producer2 producer2=new Producer2(clerk2);
        Consumer2 consumer2=new Consumer2(clerk2);

        Thread p=new Thread(producer2);
        Thread c=new Thread(consumer2);

        p.start();
        c.start();
    }
}
