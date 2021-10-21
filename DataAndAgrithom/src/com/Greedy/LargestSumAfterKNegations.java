package com.Greedy;

public class LargestSumAfterKNegations {
    public static void main(String[] args){
        int[] nums={-1,-2,-3};
        System.out.println(largestSumAfterKNegations(nums,2));
    }
    public static int largestSumAfterKNegations(int[] A, int K) {
        int minindex;
        int sum=0;
        while(K>0){
            minindex=findminindex(A);
            if(A[minindex]>=0 && K%2==0){  //数组全正并且次数为偶数不用再做变换
                sum=Sum(A);
                return sum;
            }else{
                A[minindex]=-A[minindex];
                K--;
            }
        }//变换次数变为0
        sum=Sum(A);
        return sum;
    }
    public static int Sum(int[] nums){
        int sum=0;
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
        }
        return sum;
    }
    public static int findminindex(int[] nums){
        int min=0;
        for(int i=1;i<nums.length;i++){
            if(nums[min]>nums[i])
                min=i;
        }
        return min;
    }
}
