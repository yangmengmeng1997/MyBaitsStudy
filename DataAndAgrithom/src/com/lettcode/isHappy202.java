package lettcode;

import java.util.HashSet;

public class isHappy202 {
    public static void main(String[] args) {
        System.out.println(getNext(100));
    }
    public static boolean isHappy(int n) {
        HashSet<Integer> hashSet=new HashSet<>();
        while(n!=1 && !hashSet.contains(n)){
            hashSet.add(n);
            n=getNext(n);
        }
        return n==1;
    }
    public static int getNext(int n){   //获得n的下一个的数字
        int sum=0;
        while(n!=0){
            sum=sum+(n%10)*(n%10);
            n=n/10;
        }
        return sum;
    }
    //快慢指针，产生循环结构类似于快慢链表的快慢指针
    public static boolean isHappy1(int n){
        int slow=n;
        int fast=getNext(n);
        while(fast!=1 && fast!=slow){   //只要两个相等就表示存在循环，相遇终止就行
            slow=getNext(slow);
            fast=getNext(getNext(fast));
        }
        return fast==1; //终止循环有两个条件，一个是fast==1；一个是fast==slow
    }
}
