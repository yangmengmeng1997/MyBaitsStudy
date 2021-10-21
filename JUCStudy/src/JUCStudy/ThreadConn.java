package JUCStudy;

class Number{
    private int num=0;
    public synchronized void add(){
        if(num==0){
            num++;
            notify();
            System.out.println("add方法，Number的值为"+num);
        }else{
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void del(){
        if(num==1){
            num--;
            notify();
            System.out.println("del操作" + "Number的值为"+num);
        }else{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ThreadConn {
    public static void main(String[] args) {
        Number number=new Number();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    number.add();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    number.del();
                }
            }
        }).start();
    }
}
