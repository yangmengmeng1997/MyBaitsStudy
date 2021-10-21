package lettcode;

public class reverseList206 {
    public static void main(String[] args) {
        int[] num={1,2,3,4,5};
        MyListNode head=new MyListNode();
        MyListNode tail=head;
        for(int i=0;i<num.length;i++){
            MyListNode node=new MyListNode(num[i]);
            tail.next=node;
            tail=node;  //尾插构造原始列表
        }
        PrintList(head.next);
        //MyListNode newhead=reverseList(head.next);
        MyListNode newhead=reverseList1(head.next);
        PrintList(newhead);
    }
    public static void PrintList(MyListNode node){
        while (node!=null){
            System.out.println(node.val);
            node=node.next;
        }
    }
    public static MyListNode reverseList(MyListNode head) {
        MyListNode newhead=new MyListNode();
        while(head!=null){   //遍历原来的链表，用头插法
            //必须就是这样创建新的节点
            //而不是temp=head.next。这样进过下面调换之后就是破坏了原来的结构
            //这样就是浪费空间
            MyListNode temp=new MyListNode(head.val);
            temp.next=newhead.next;
            newhead.next=temp;
            head=head.next;   //线索遍历
        }
        return newhead.next;
    }

    public static MyListNode reverseList1(MyListNode head){
        MyListNode pre=null;
        MyListNode cur=head;
        while(cur!=null){
            MyListNode temp=cur.next;  //保存下一个节点，不然之后会修改位置
            cur.next=pre;   //改变指向
            pre=cur;   // 更新节点
            cur=temp;
        }
        return pre;  //最后节点
    }
}

//单链表结点
class MyListNode{
    int val;
    MyListNode next;
    MyListNode(){};
    MyListNode(int val) { this.val = val;}
}
