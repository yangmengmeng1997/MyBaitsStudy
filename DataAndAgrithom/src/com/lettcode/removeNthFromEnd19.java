package lettcode;

import com.sun.deploy.pings.Pings;

public class removeNthFromEnd19
{
    public static void main(String[] args) {
        int[] num ={1,2,3,4,5};
        ListNode head=new ListNode();
        ListNode tail=head;
        for(int i=0;i<num.length;i++){
            ListNode tempNode=new ListNode(num[i]);
            tail.next=tempNode;
            tail=tempNode;   //尾插法
        }
        PrintNode(head.next);
        PrintNode(removeNthFromEnd(head.next,2));
    }
    public static void PrintNode(ListNode head){
        while(head!=null){
            System.out.println(head.val);
            head=head.next;
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy_head=new ListNode();
        dummy_head.next=head;
        ListNode fast=dummy_head;
        ListNode slow=dummy_head;
        for(int i=0;i<=n;i++){
            fast=fast.next;
        } //双指针  移到n+1位置，这样fast为空正好移到删除节点前一个
        while(fast!=null){
            slow=slow.next;
            fast=fast.next;
        }
        slow.next=slow.next.next;   //删除对应节点
        return dummy_head.next;
    }
}
