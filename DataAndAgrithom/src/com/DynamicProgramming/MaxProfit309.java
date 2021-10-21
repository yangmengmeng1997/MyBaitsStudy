package com.DynamicProgramming;

public class MaxProfit309 {
    public static void main(String[] args) {
        int[] prices={1,2,3,0,2};
        System.out.println(maxProfit(prices));
    }
    public static int maxProfit(int[] prices) {
        //三种状态
        //0 表示持有股票状态
        //1 表示不持有股票状态并且处于冷冻期
        //2 表示不持有股票并且不处于冷冻期状态
        int[][] dp=new int[prices.length][3];
        dp[0][0]=-prices[0];  //第一天持有即使买入价格已经就是0-price【0】
        dp[0][1]=0;
        dp[0][2]=0;  //第一天没有股票可卖所以现金为0
        for(int i=1;i<prices.length;i++){
            //1状态的前一天必是卖出股票不考虑，现在时买入股票，当然是减
            dp[i][0]=Math.max(dp[i-1][0],dp[i-1][2]-prices[i]);
            //1状态的前一天必是卖出股票，所以鄙视持有状态
            dp[i][1]=dp[i-1][0]+prices[i];
            //2状态转换,第i-1天是冷冻期，但是没有解冻，到了i天才可以解冻，但是现金不变
            dp[i][2]=Math.max(dp[i-1][2],dp[i-1][1]);
        }
        //手上不持有股票时有最多的钱
        return Math.max(dp[prices.length-1][1],dp[prices.length-1][2]);
    }
}
