package com.DynamicProgramming;

public class NumSquares279 {
    public static void main(String[] args) {
        System.out.println(numSquares(13));
    }
    public static int numSquares(int n){
        int[] nums=new int[100];
        for(int i=0;i<100;i++)
            nums[i]=(i+1)*(i+1);
        int[] dp=new int[n+1];
        dp[0]=0;
        for(int i=1;i<=n;i++)
            dp[i]=Integer.MAX_VALUE;   //初始化dp数组
        for(int i=0;i<nums.length;i++)
            for(int j=nums[i];j<=n;j++){
                if(dp[j-nums[i]]!=Integer.MAX_VALUE)
                    dp[j]=Math.min(dp[j],dp[j-nums[i]]+1);
            }
        return dp[n];
    }
}
