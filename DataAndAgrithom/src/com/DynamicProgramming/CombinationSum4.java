package com.DynamicProgramming;

public class CombinationSum4 {
    public static void main(String[] args) {
        int[] nums={1,2,3};
        System.out.println(combinationSum4(nums,4));
    }
    public static int combinationSum4(int[] nums, int target) {
        int[] dp=new int[target+1];
        dp[0]=1;
        for(int j=0;j<=target;j++)  //特定循环位置
            for(int i=0;i<nums.length;i++){
                if(j>=nums[i])
                    dp[j]+=dp[j-nums[i]];
            }
        return dp[target];
    }
}
