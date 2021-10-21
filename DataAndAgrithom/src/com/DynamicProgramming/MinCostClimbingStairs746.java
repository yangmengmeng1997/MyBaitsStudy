package com.DynamicProgramming;

public class MinCostClimbingStairs746 {
    public static void main(String[] args) {
        int[] cost = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        System.out.println(minCostClimbingStairs(cost));
    }
    public static int minCostClimbingStairs(int[] cost) {
         int[] dp=new int[cost.length+1];
         dp[0]=dp[1]=0;  //初始站在0号台阶或者1号台阶不耗费体力
         for(int i=2;i<dp.length;i++){
             //当前台阶耗费地体力+走上之前台阶共耗费地体力
             dp[i]=Math.min(dp[i-1]+cost[i-1],dp[i-2]+cost[i-2]);
         }
         return dp[dp.length-1];
    }
}
