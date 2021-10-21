package com.WeeklyContest;

public class GetConcatenation5808 {
    public static void main(String[] args) {
        int[] nums={1,2,3};
        int[] ans=getConcatenation(nums);
        for(int i=0;i<ans.length;i++)
            System.out.println(ans[i]);
    }
    public static int[] getConcatenation(int[] nums) {
        int[] ans=new int[nums.length*2];
        int n=nums.length;
        for(int i=0;i<n;i++){
            ans[i]=nums[i];
            ans[i+n]=nums[i];
        }
        return ans;
    }
}
