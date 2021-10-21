package lettcode;


import sun.security.krb5.internal.ktab.KeyTabInputStream;

import java.util.HashMap;
import java.util.HashSet;

public class hasCycle141 {
    public static void main(String[] args) {
        int[] head = {3,2,0,-4};
        int pos =-1;   //用来构造链表用的
        ListNode MyHead=Create(head,pos);
        System.out.println(hasCycle1(MyHead.next));
    }
    //对于hashset的一些常用方法还是不太了解
    public static boolean hasCycle(ListNode head) {
        HashSet<ListNode> hashSet=new HashSet<>();
        while(head!=null){
            if(!hashSet.add(head))  //一直循环总能有相同的节点被添加，就不会添加成功
                return true;
            head=head.next;
        }
        return false;  //while循环能结束就是说明不存在环
    }
    //快慢指针
    public static boolean hasCycle1(ListNode head){
        if(head==null || head.next==null) return false; //空链表或者单链表就是没有环
        ListNode fast=head.next;
        ListNode slow=head;
        while(slow!=fast){
            if(fast==null || fast.next==null) return false;   //没有循环
            slow=slow.next;
            fast=fast.next.next;
        }
        return true;   //快慢指针相遇，判断有环
    }
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
