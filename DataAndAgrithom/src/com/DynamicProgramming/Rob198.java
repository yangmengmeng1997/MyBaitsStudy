package com.DynamicProgramming;

public class Rob198 {
    public static void main(String[] args) {
        int[] nums={2,1,1,2};
        System.out.println(rob(nums));
    }
    public static int rob(int[] nums) {
        if(nums.length==1) return nums[0];
        int[] dp=new int[nums.length];
        dp[0]=nums[0];
        dp[1]=Math.max(dp[0],dp[1]);
        for(int i=2;i<nums.length;i++){
            dp[i]=Math.max(dp[i-1],dp[i-2]+nums[i]);  //递推公式
        }
        return dp[nums.length-1];
    }
}
