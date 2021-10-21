package com.WeeklyContest;

public class getLucky5823 {
    public static void main(String[] args) {
        System.out.println(getLucky("leetcode",2));
    }
    public static int getLucky(String s, int k) {
        int sum=0;
        StringBuffer trans=new StringBuffer();
        for(int i=0;i<s.length();i++){
            trans.append(s.charAt(i)-'a'+1);
        }
        for(int i=1;i<=k;i++){
            sum=0;
            for(int j=0;j<trans.length();j++){
                sum+=trans.charAt(j)-'0';
            }
            trans=new StringBuffer(Integer.toString(sum));
        }
        return sum;
    }
}
