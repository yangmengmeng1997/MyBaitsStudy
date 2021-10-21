package com.Greedy;

public class Candy {
    public static void main(String[] args) {
        int []nums={1,2,2,5,4,3,2};
        System.out.println(candy(nums));
    }
    public static int candy(int[] ratings){
        int[] candiys=new int[ratings.length];
        candiys[0]=1;
        int sum=0;
        //从左向右遍历
        for(int i=1;i<ratings.length;i++){
            if(ratings[i]>ratings[i-1]){
                candiys[i]=candiys[i-1]+1;
            }else
                candiys[i]=1;
        }
        //从右向左倒序遍历
        for(int i=ratings.length-2;i>=0;i--){
            if(ratings[i]>ratings[i+1]){
                candiys[i]=Math.max(candiys[i],candiys[i+1]+1);
            }
        }
        for(int i=0;i<candiys.length;i++){
            sum+=candiys[i];
        }
        return sum;
    }
}
