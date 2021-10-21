package ThreadStudy;

//生产者和消费者综合问题实现
//产品池
class Clerk{
    private int productCount=0;

    //定义消费产品,调用wait或者notify时一定要加同步方法标识
    public synchronized void ProduceProduct() {
        if(productCount<20){
            productCount++;
            System.out.println(Thread.currentThread().getName()+"生产第"+productCount+"产品");
            notify();
        }else{
            try {
                System.out.println("生产池已满，等待消费。。。");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //定义生产产品
    public synchronized void ConsumerProduct(){
        if(productCount>0){
            productCount--;
            System.out.println(Thread.currentThread().getName()+"消费第"+productCount+"产品");
            notify();
        }else{
            try {
                System.out.println("生产池已空，等待生产。。。");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
//生产者,定义私有共用一个
class Producer extends Thread{
    private Clerk clerk;
    public Producer(Clerk c){
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
class Consumer extends Thread{
    private Clerk clerk;
    public Consumer(Clerk c){
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

public class ProduceAndConsumer {
    public static void main(String[] args) {
        Clerk clerk=new Clerk();
        Producer producer=new Producer(clerk);
        Consumer consumer=new Consumer(clerk);

        Thread p=new Thread(producer);
        Thread c=new Thread(consumer);

        p.start();
        c.start();
    }
}
