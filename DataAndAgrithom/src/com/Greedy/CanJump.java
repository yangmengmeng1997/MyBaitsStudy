package com.Greedy;

public class CanJump {
    public static void main(String[] args){
        int[] nums={3,2,1,0,4};
        System.out.println(canJump(nums));
    }
    public static boolean canJump(int[] nums) {
        int maxlength=nums[0];
        for(int i=1;i<nums.length-1;i++){
            if(maxlength>=i){
                if(maxlength<nums[i]+i){
                    maxlength=nums[i]+i;
                }
            }
        }
        if(maxlength>=nums.length-1) return true;
        else return false;
    }
}
