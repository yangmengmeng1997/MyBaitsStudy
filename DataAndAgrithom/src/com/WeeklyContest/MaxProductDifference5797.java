package com.WeeklyContest;

public class MaxProductDifference5797 {
    public static void main(String[] args) {
        int[] nums={5,6,2,7,4};
        System.out.println(maxProductDifference(nums));
    }
    //考察排序，排序选最大的两个相乘再和两个最小的相乘做差
    public static int maxProductDifference(int[] nums) {
        int length=nums.length;
        //垃圾冒泡
        for(int i=0;i<length;i++)
            for(int j=0;j<length;j++){
                if(nums[i]<nums[j]){
                    int temp=nums[i];
                    nums[i]=nums[j];
                    nums[j]=temp;
                }
            }
        return nums[length-1]*nums[length-2]-nums[0]*nums[1];
    }
}
