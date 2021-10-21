package lettcode;

public class removeElements203 {
    public static void main(String[] args) {
        int[] num ={1,2,6,3,4,5,6};
        ListNode head=new ListNode(num[0]);
        ListNode tail=head;
        for(int i=1;i<num.length;i++){
            ListNode tempNode=new ListNode(num[i]);
            tail.next=tempNode;
            tail=tempNode;   //尾插法
        }
        PrintNode(removeElements(head,6));
    }
    public static void PrintNode(ListNode head){
        while(head!=null){
            System.out.println(head.val);
            head=head.next;
        }
    }
    public static ListNode removeElements(ListNode head, int val) {
        if(head==null) return null;
        ListNode newhead=new ListNode();
        newhead.next=head;   //设置新的节点作为头结点
        ListNode preNode=newhead;
        ListNode curnode=head;
        while(curnode!=null){
            if(curnode.val==val) {
                preNode.next = curnode.next;   //统一删除操作
            }
            preNode=preNode.next;  //这样写到最后直接指向6，而curnode也指向6，已经失去前置节点的意义
            curnode=curnode.next;
        }
        return newhead.next;

//        if(head==null)  return null;
//        ListNode newhead=new ListNode();
//        ListNode tail=newhead;
//        ListNode curnode=head;
//        while(curnode!=null){
//            if(curnode.val!=val){
//                tail.next=new ListNode(curnode.val);  //构造新节点才能断开原来的连接
//                tail=tail.next;   //尾插法插入新节点
//            }
//            curnode=curnode.next;  //原来的链表后移
//        }
//        return newhead.next;
    }
}
class ListNode{
    int val;
    ListNode next;
    ListNode(){};
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
