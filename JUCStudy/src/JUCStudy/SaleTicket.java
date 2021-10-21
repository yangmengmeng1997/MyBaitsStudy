package JUCStudy;

class Ticket {
    private int ticket=30;
    //买票
    public synchronized void sale(){
        if(ticket>0){
            ticket--;
            System.out.println(Thread.currentThread().getName()+"卖出一张票，当前剩余票数为:"+ticket);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else
            System.out.println("票数不足");
    }

}

public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket=new Ticket();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    ticket.sale();
                }

            }
        },"AA").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    ticket.sale();
                }

            }
        },"BB").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    ticket.sale();
                }

            }
        },"CC").start();
    }
}
