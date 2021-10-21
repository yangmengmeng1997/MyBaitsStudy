package com.Greedy;

public class WiggleMaxLength {
    public static void main(String[] args){
        int[] nums={0,0,0};
        System.out.println(wiggleMaxLength(nums));
    }
    public static int wiggleMaxLength(int[] nums) {
        if(nums.length<=1) return 1;
        int result=1;
        int prediff=0;
        int curdiff=0;
        for(int i=1;i<nums.length;i++){
           curdiff=nums[i]-nums[i-1];
           if((curdiff>0 && prediff<=0) ||(curdiff<0 && prediff>=0)){
               result++;
               prediff=curdiff;
           }
        }
        return result;
    }

}
