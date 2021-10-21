package com.DynamicProgramming;

public class Rob213 {
    public static void main(String[] args) {
        int[] nums={0,1,1,1};
        System.out.println(rob1(nums));
    }
    /*
     整体的逻辑有问题的
     比如1 2 3 1  这样一比较就是选1 3
     比如1 2 1 1 这样一比较就是选2 1
     但是这样无形中就是考虑放到了在首尾必选一个的情况，结果是错的
     可以考虑偷首尾，但是没必要进行一定要偷首尾才行的（只偷中间是可以的）。
     */
    public static int rob(int[] nums) {
        if(nums.length==1) return nums[0];
        if(nums.length==2) return Math.max(nums[0],nums[1]);
        int[] dp=new int[nums.length];
        if(nums[0]>=nums[nums.length-1]){
            dp[0]=nums[0];
            dp[1]=Math.max(nums[0],nums[1]);
            for(int i=2;i<=nums.length-2;i++){
                dp[i]=Math.max(dp[i-2]+nums[i],dp[i-1]);
            }
            return dp[nums.length-2];
        }else{
            //逻辑问题
            dp[nums.length-1]=nums[nums.length-1];
            dp[nums.length-2]=Math.max(dp[nums.length-1],nums[nums.length-2]);
            for(int i=nums.length-3;i>=1;i--){
                dp[i]=Math.max(dp[i+1],dp[i+2]+nums[i]);
            }
        }
        return dp[1];
    }

    public static int rob1(int[] nums){
        if(nums.length==1) return nums[0];
        if(nums.length==2) return Math.max(nums[0],nums[1]);
        int result1=helper(nums,0,nums.length-2);  //考虑第一个房子
        int result2=helper(nums,1,nums.length-1);  //考虑最后一个房子
        return Math.max(result1,result2);
    }
    public static int helper(int[] nums,int start,int end){
        int[] dp=new int[end+1];
        dp[start]=nums[start];
        dp[start+1]=Math.max(nums[start],nums[start+1]);
        for(int i=start+2;i<=end;i++){
            dp[i]=Math.max(dp[i-1],dp[i-2]+nums[i]);
        }
       return dp[end];
    }
}
