package lettcode;

import java.util.HashSet;

public class detectCycle142 {
    public static void main(String[] args) {
        int[] head = {3,2,0,-4};
        int pos =-1;   //用来构造链表用的
        ListNode MyHead=Create(head,pos);
    }
    //利用hashset判重，循环添加所有节点，第一次添加失败的节点就作为入环第一个节点返回
    public ListNode detectCycle(ListNode head){
        HashSet<ListNode> hashSet=new HashSet<>();
        while (head!=null){
            if(!hashSet.add(head)) return head;  //第一次添加失败就返回
            head=head.next;
        }
        return null;  //链表无环返回null
    }
    //构建环形链表
    public static ListNode Create(int[] num,int pos){
        ListNode head=new ListNode();
        ListNode tail=head;
        ListNode ciclenode=null;
        for(int i=0;i<num.length;i++){
            ListNode node=new ListNode(num[i]);
            tail.next=node;
            tail=node;
            if(i==pos) ciclenode=node;   //记录位置，表示环
        }
        tail.next=ciclenode;    //构造环，没有路就是null;
        return head;
    }
}
