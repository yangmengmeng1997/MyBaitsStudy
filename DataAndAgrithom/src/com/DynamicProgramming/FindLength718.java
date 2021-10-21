package com.DynamicProgramming;

public class FindLength718 {
    public static void main(String[] args) {
        int[] nums1={1,1,1};
        int[] nums2={1,1,1};
        System.out.println(findLength(nums1,nums2));
    }
    public static int findLength(int[] nums1, int[] nums2) {
        int max=0;
        int[][] dp=new int[nums1.length+1][nums2.length+1];
        for(int i=1;i<=nums1.length;i++)
            for(int j=1;j<=nums2.length;j++){
                if(nums1[i-1]==nums2[j-1]){
                    dp[i][j]=dp[i-1][j-1]+1;  //递推式要画图理解
                    if(dp[i][j]>max) max=dp[i][j];
                }
            }
        return max;
    }
}
