package com.DynamicProgramming;


//简单DP
public class FindLengthOfLCIS600 {
    public static void main(String[] args) {
        int[] nums={1,4,2,3,1};
        System.out.println(findLengthOfLCIS(nums));
    }
    public static int findLengthOfLCIS(int[] nums) {
        int maxlength=1;
        int[] dp=new int[nums.length];
        for(int i=0;i<dp.length;i++)
            dp[i]=1;
        for(int i=1;i<nums.length;i++){
            if(nums[i]>nums[i-1]){
                dp[i]=dp[i-1]+1;
            }else
                dp[i]=1;
            if(dp[i]>maxlength)  maxlength=dp[i];
        }
        return maxlength;
    }
}
