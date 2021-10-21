package com.WeeklyContest;

public class addRungs5814 {
    public static void main(String[] args) {
        int[] rungs={3};
        int dist=1;
        System.out.println(addRungs(rungs,dist));
    }
    public static int addRungs(int[] rungs, int dist) {
        int[] dp=new int[rungs.length];
        if(rungs[0]>dist){
            if(rungs[0]%dist==0) dp[0]=rungs[0]/dist-1;  //比如6/2=3 但是只要加2个台阶
            else dp[0]=rungs[0]/dist;   //比如5/2=2 就足够加了
        }
        for(int i=1;i<rungs.length;i++){
            int temp=rungs[i]-rungs[i-1];
            if(temp>dist){
                if(temp%dist==0) dp[i]=temp/dist-1+dp[i-1];  //比如6/2=3 但是只要加2个台阶
                else dp[i]=temp/dist+dp[i-1];   //比如5/2=2 就足够加了
            }
            else
                dp[i]=dp[i-1];   //否则不要架梯子
        }
        return dp[rungs.length-1];
    }
}
