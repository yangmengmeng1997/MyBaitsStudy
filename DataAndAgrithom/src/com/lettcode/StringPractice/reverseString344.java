package lettcode.StringPractice;

public class reverseString344 {
    public static void main(String[] args) {
        char[] s={'h','e','l','l','o'};
        reverseString(s);
    }
    public static void reverseString(char[] s) {
        int left=0;
        int right=s.length-1;
        char temp;
        while(left<right){
            temp=s[left];
            s[left]=s[right];
            s[right]=temp;
            left++;
            right--;
        }
        for(char i:s){
            System.out.print(i);
        }
    }
}
