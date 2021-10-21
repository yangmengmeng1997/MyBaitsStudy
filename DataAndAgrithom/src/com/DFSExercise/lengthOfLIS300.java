package com.DFSExercise;

public class lengthOfLIS300 {
    public static void main(String[] args) {
        int[] nums = {1,3,6,7,9,4,10,5,6};
        System.out.println(lengthOfLIS(nums));
    }
    //最长递增子序列
    public static int lengthOfLIS(int[] nums) {
        int result=0;
        int[] dp=new int[nums.length];
        for(int i=0;i<dp.length;i++) dp[i]=1;  //每一个元素可以最少构成一个长度为1的自序列
        for(int i=1;i<nums.length;i++){
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    //只要nums[i]>nums[j]，那么就可以将第i和元素挂在子序列后面，长度加1
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
            }
            if(dp[i]>result) result=dp[i];
        }
        return result;
    }
}
