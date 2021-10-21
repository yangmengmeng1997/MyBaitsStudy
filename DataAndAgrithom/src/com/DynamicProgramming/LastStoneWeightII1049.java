package com.DynamicProgramming;

import java.util.Arrays;

public class LastStoneWeightII1049 {
    public static void main(String[] args) {
        int[] nums={2,2};
        System.out.println(lastStoneWeightII(nums));
    }
    //类似将数组划分等和子集问题
    public static int lastStoneWeightII(int[] stones) {
        int target= Arrays.stream(stones).sum();
        int halfsum=target/2;
        int[] dp=new int[halfsum+1];
        for(int i=0;i<stones.length;i++)
            for(int j=halfsum;j>=stones[i];j--){
                dp[j]=Math.max(dp[j],dp[j-stones[i]]+stones[i]);
            }
        return target-dp[halfsum]-dp[halfsum];
    }
}
