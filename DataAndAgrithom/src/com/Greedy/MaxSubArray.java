package com.Greedy;

public class MaxSubArray {
    public static void main(String[] args)
    {
        int[] nums={-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(maxSubArray(nums));
    }
    public static int maxSubArray(int[] nums) {
        int maxsum=Integer.MIN_VALUE;
        int count=0;
        for(int i=0;i<nums.length;i++){
            count=count+nums[i];   //累加和
            if(maxsum<count) maxsum=count;  //result记录最大和
            if(count<0) count=0;  //和为负数就是要重置了
        }
        return maxsum;
    }
}
