package com.DynamicProgramming;

public class IntegerBreak343 {
    public static void main(String[] args) {
        integerBreak(10);
    }
    public static int integerBreak(int n) {
        int[]dp =new int[n+1];
        dp[1]=dp[2]=1;  //dp[i]表示i的最大乘积
        for(int i=3;i<=n;i++){
            for(int j=1;j<=i;j++){   //将n拆分为1+n-1,2+n-2.....
                int maxmul=Math.max(j*(i-j),j*dp[i-j]);
                dp[i]=Math.max(dp[i],maxmul);  //取i拆分为多种组合选取的最大乘积
            }
        }
        return dp[n];
    }
}
