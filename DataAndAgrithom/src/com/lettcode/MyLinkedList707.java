package lettcode;

public class MyLinkedList707 {
    public static void main(String[] args) {
        MyLinkedList obj = new MyLinkedList();
        obj.addAtHead(1);
        obj.addAtHead(2);
        obj.addAtHead(3);
        obj.addAtTail(4);
        obj.addAtTail(5);
        obj.addAtIndex(5,100);
        //obj.PrintList(obj.head.next);
        obj.deleteAtIndex(5);
        obj.PrintList(obj.head.next);
    }
}


class Node{
    int val;
    Node next;
    Node(){}
    Node(int val) {
        this.val=val;
    }
}   //单个链表节点构造

class MyLinkedList {       //整体链表的构造
    int size;    //size的设计
    Node head;   //构造虚拟头结点
    /** Initialize your data structure here. */
    public MyLinkedList() {
        size=0;     //这种设计思想
        head=new Node(0);   //虚拟节点的值不在正常的val之间
    }
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if(index<0 || index>size-1) return -1;
        int count=0;
        Node target=head.next;
        while(true){
            if(count==index){
                break;
            }else {
                count++;
                target=target.next;
            }
        }
        return target.val;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        Node temp=new Node(val);
        temp.next=head.next;
        head.next=temp;    //头插
        size++;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        Node tail=head;   //指向头元素
        while(tail.next!=null) tail=tail.next;  //遍历到尾部
        Node temp=new Node(val);
        tail.next=temp;
        size++;
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        Node newnode=new Node(val);
        if(index==size){
            Node temp=head;
            while(temp.next!=null) temp=temp.next;
            temp.next=newnode;   //尾插即可
        }else if(index>size){
            return;
        }else if(index<=0){   //头插
             newnode.next=head.next;
             head.next=newnode;
        }else{     //index 没有超出范围
            Node preNode=head.next;
            int start=0;
            while(start++<index-1){
                preNode=preNode.next;   //遍历链表
            }
            newnode.next=preNode.next;
            preNode.next=newnode;
        }
        size++;    //添加完毕就是数量加1
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if(index<0 || index>=size) return;
        int start=0;
        Node preNode=head;
        while(start++<index){
            preNode=preNode.next;
        }//找到待删除节点的前一个节点
        preNode.next=preNode.next.next;
        size--;   //删除后需要做减去元素个数啊，不然个数对不上
    }
    public void PrintList(Node head){
        while (head!=null){
            System.out.println(head.val);
            head=head.next;
        }
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
