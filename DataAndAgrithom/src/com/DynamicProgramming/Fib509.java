package com.DynamicProgramming;

public class Fib509 {
    public static void main(String[] args) {
        System.out.println(fib(6));
    }
    public static int fib(int n) {
        if(n==0) return 0;
        if(n==1) return 1;
       int[] dp=new int[3];
       dp[0]=0;
       dp[1]=1;
       for(int i=2;i<n+1;i++){
           dp[2]=dp[0]+dp[1];
           //跟新dp的值，这样只需要维护三个值而不用维护整个数组
           dp[0]=dp[1];
           dp[1]=dp[2];
       }
       return dp[2];
    }
}
