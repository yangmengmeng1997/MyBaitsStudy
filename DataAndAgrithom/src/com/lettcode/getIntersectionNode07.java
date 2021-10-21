package lettcode;

public class getIntersectionNode07 {
    public static void main(String[] args) {
        int[] num1={4,1,8,4,5};
        int[] num2={5,0,1,8,4,5};
        ListNode head1=Create(num1);
        ListNode head2=Create(num2);
        System.out.println(getIntersectionNode(head1,head2).val);
    }
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int len1 = length(headA.next);
        int len2 = length(headB.next);
        ListNode start;
        ListNode temp;
        if (len1 > len2) {
            start = headA.next;
            for (int i = 0; i < len1 - len2; i++) {
                start = start.next;
            }
            temp = headB.next;
            while (start != null) {
                if (start.val == temp.val) return start;
                start = start.next;
                temp = temp.next;
            }
        } else {
            start = headB.next;
            for (int i = 0; i < len2 - len1; i++) {
                start = start.next;
            }
            temp = headA.next;
            while (start != null) {
                if (start.val == temp.val) return start;
                start = start.next;
                temp = temp.next;
            }
        }
        return start;
    }
    public static int length(ListNode head){
        int sum=0;
        while(head!=null){
            sum++;
            head=head.next;
        }
        return sum;
    }
    public static ListNode Create(int[] num){
        ListNode dummy_head=new ListNode();
        ListNode tail=dummy_head;
        for(int i=0;i<num.length;i++){
            ListNode node=new ListNode(num[i]);
            tail.next=node;
            tail=node;
        }
        return dummy_head;
    }

}
