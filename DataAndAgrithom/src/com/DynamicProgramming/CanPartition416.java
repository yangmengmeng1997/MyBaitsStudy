package com.DynamicProgramming;

import java.util.Arrays;

public class CanPartition416 {
    public static void main(String[] args) {
        int[] nums={1,5,11,5};
        System.out.println(canPartition(nums));
    }
    //抽象为0-1背包问题
    public static boolean canPartition(int[] nums) {
        int halfsum=Arrays.stream(nums).sum();  //API数组求和
        if(halfsum%2==1) return false;
        halfsum=halfsum/2;    //元素和必须要拆分为1半才行，不然就不存在等和子集
        int []dp=new int[halfsum+1];
        for(int i=0;i<nums.length;i++)
            for(int j=halfsum;j>=nums[i];j--){
                dp[j]=Math.max(dp[j],dp[j-nums[i]]+nums[i]);
            }
        //System.out.println(dp[halfsum]);
        return dp[halfsum]==halfsum;
    }
}
