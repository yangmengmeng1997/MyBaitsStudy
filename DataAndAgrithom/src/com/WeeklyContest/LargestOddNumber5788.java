package com.WeeklyContest;

public class LargestOddNumber5788 {
    public static void main(String[] args) {
        String num="52";
        System.out.println(largestOddNumber(num));
    }
    //超时，这是因为从前往后找，外加双重for循环会超时
    //直接从后往前找，找到一个末尾是奇数的字符，那么从头到这个字符就是奇数字符
    public static String largestOddNumber(String num) {
        String max="";
        for(int i=0;i<num.length();i++){
            if((num.charAt(i)-'0')%2==1){
                String str=num.substring(0,i+1);
                if(compare(str,max)) max=str;
            }
        }
        return max;
    }
    //判断是s1是否大于s2
    public static boolean compare(String s1,String s2){
        if(s1.length()>s2.length()) return true;
        else if(s1.length()<s2.length()) return false;
        else{
            for(int i=0;i<s1.length();i++){
                if(s1.charAt(i)>s2.charAt(i)) return true;
                else if(s1.charAt(i)<s2.charAt(i)) return false;
                else continue;
            }
        }
        return false;  //s1和s2相等返回false
    }
}
