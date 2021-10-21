package lettcode;

import java.nio.channels.Pipe;

public class swapPairs24 {
    public static void main(String[] args) {
        MyNode head=new MyNode();
        MyNode tail=head;
        int[] num={1,2,3,4};
        for(int i=0;i<num.length;i++){
            MyNode temp=new MyNode(num[i]);
            tail.next=temp;
            tail=temp;
        }
        PrintNode(head.next);
        PrintNode(swapPairs(head.next));
    }
    public static MyNode swapPairs(MyNode head) {
        if(head==null || head.next==null) return head;
        MyNode dummy_head=new MyNode();
        dummy_head.next=head;
        MyNode cur=dummy_head;
        //while中判断虚拟指针后面还有没有一个和两个节点
        while(cur.next!=null && cur.next.next!=null){
            MyNode temp1=cur.next;     //一个是节点
            MyNode temp2=cur.next.next.next;

            //这句话是什么意思，对吧，那你为什么不用temp1代替cur.next对吧，就是这个道理
            //不对cur.next不能再用temp替换，但是cur.next=temp1.next中cur.next已经变化了
            cur.next=temp1.next;
            cur.next.next=temp1;
            cur.next.next.next=temp2;  //这是cur.next.next这个对象的指针指向了temp2这个对象就不一样

            cur=cur.next.next;  //移动两个顶点
        }
        return dummy_head.next;
    }

    public static void PrintNode(MyNode head){
        while(head!=null){
            System.out.println(head.val);
            head=head.next;
        }
    }
}
class MyNode{
    int val;
    MyNode next;
    MyNode() {}
    MyNode(int val) { this.val = val; }
    MyNode(int val, MyNode next) { this.val = val; this.next = next; }
}
