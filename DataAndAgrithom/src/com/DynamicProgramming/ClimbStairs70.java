package com.DynamicProgramming;

public class ClimbStairs70 {
    public static void main(String[] args) {
        System.out.println(climbStairs1(3));
    }
    public static int climbStairs(int n) {
        if(n<=2) return n;
        int[] dp=new int[n+1];
        dp[1]=1;
        dp[2]=2;
        for(int i=3;i<n+1;i++){
            dp[i]=dp[i-1]+dp[i-2];
        }
        return dp[n];
    }

    //动态规划问题，完全背包动态规划问题
    public static int climbStairs1(int n) {
        int[] nums={1,2};        //爬楼梯一阶或者两阶，也可以扩展到一次爬m阶
        int[] dp=new int[n+1];
        dp[0]=1;
        for(int j=0;j<=n;j++)
            for(int i=0;i<nums.length;i++){
                if(j>=nums[i]){
                    dp[j]+=dp[j-nums[i]];
                }
            }
        return dp[n];
    }
}
