package com.Hash;

import java.util.Scanner;

/**
 * @author ymm
 * @create 2020--11--14:12
 * 哈希表是由数组和链表组合而成：一个数组的每个元素都存放的是一条链表
 */
public class HashMap {
    public static void main(String []args){
        HashTable hash=new HashTable(7);
        String key="";
        Scanner sc=new Scanner(System.in);
        int flag=0;
        while(true){
            System.out.println("add:添加库员");
            System.out.println("list:显示库员");
            System.out.println("find:查找库员");
            System.out.println("exit:退出");
            key=sc.next();
            switch (key){
                case "add":
                    System.out.println("输入id");
                    int id=sc.nextInt();
                    System.out.println("输入名字");
                    String name=sc.next();
                    Emp emp=new Emp(id,name);
                    hash.add(emp);
                    break;
                case "list":
                    hash.travel();
                    break;
                case "find":
                    System.out.println("输入查找id号");
                    id=sc.nextInt();
                    hash.findbyid(id);
                    break;
                default:
                    flag=1;
                    break;
            }
        if(flag==1)
            break;
        }

    }


}

//管理多条链表
class HashTable{
    private Linkedlist[] emplist;   //存放链表的数组
    private int size;
    //构造函数,初始化数组
    public HashTable(int size){
        this.size=size;
        emplist=new Linkedlist[size];  //这里只是建立数组，数组里的每一个链表没有初始化，也就是空
        //初始化链表,创建链表，为每个链表分配空间
        for(int i=0;i<size;i++){
            emplist[i]=new Linkedlist();
        }
    }
    //添加数据，多条链表决定添加某一条是由hashmap决定的，而内部添加节点是由单独的某一条链表来决定的
    public void add(Emp emp){
        //根据员工的id得到应该添加到哪个链表（链表号为0,1,2,3,4,5.。。。。）
        int empnumber=hashFun(emp.id);
        //加入到对应的链表中 emplist[empnumber]选中对应的链表,用链表对应的方法
        emplist[empnumber].add(emp);
    }
    //遍历所有的链表
    public void travel(){
        for(int i=0;i<size;i++){
            emplist[i].list(i);
        }
    }
    //根据id查找相对应的数据id号码
    public void findbyid(int id){
         int LinkedlistNO=hashFun(id);   //id号散列找到对应的列表号
         Emp emp = emplist[LinkedlistNO].findbyid(id);  //找到对应链表的，用链表的找id方法
         if(emp!=null){
             System.out.printf("在第%d链表上找到该雇员,id=%d\n",LinkedlistNO,id);
         }
         else
             System.out.println("没有找到对应的雇员");
    }

    //哈希表的散列函数,简单用取模函数
    public int hashFun(int id){
        return id%size;
    }

}



//这是链表的元素节点
class Emp{
    int id;
    String name;
    Emp next;
    public Emp(int id,String name){
        this.id=id;
        this.name=name;
    }
}

//定义数组中的链表结构，表示数组中的元素
class Linkedlist{
    //头指针,不用head节点
    private Emp head;  //默认为空
    //实现增删改查
    public void add(Emp emp){
        if(head==null){
            head=emp;
            return ;
        }
        //尾插法
        Emp cur=head;
        while(cur.next!=null)
        {
            cur=cur.next;
        }  //一直到移动到最后一位
        //加入新节点
        cur.next=emp;
    }
    //遍历
    public void list(int no){
        if(head==null){
            System.out.println("第"+no+"链表为空");
            return ;
        }
        System.out.print("第"+no+"链表信息为");
        Emp temp=head;
        while(temp!=null)
        {
            System.out.printf("=>id=%d,name=%s",temp.id,temp.name);
            temp=temp.next;
        }
        System.out.println();
    }

    //根据id查找特定人
    public Emp findbyid(int id){
        if(head==null){
            //System.out.println("链表为空");
            return null;
        }
        Emp temp=head;
        while (temp!=null){   //遍历整个链表
            if(temp.id==id)
                return temp;
            temp=temp.next;
        }
        return null;
    }
    //删除
    public void deletebyid(int id){
        if(head==null){
            return;
        }
        if(head.next==null){  //删除
            head=null;
            return;
        }
        Emp temp=head;
        while(temp.next!=null){  //最后一个节点停止
            if(temp.next.id==id){
                temp.next=temp.next.next;
                return ;
            }
        }
        
    }


}