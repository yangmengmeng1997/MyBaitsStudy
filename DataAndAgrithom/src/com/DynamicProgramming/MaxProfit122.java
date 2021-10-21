package com.DynamicProgramming;

public class MaxProfit122 {
    public static void main(String[] args) {
        int[] prices={7,1,5,3,6,4};
        System.out.println(maxProfit(prices));
    }
    //动态规划
    public static int maxProfit(int[] prices){
        int[][] dp=new int[prices.length][2];  //0 表示持有股票,1表示不持有股票
        dp[0][0]=-prices[0];
        dp[0][1]=0;
        for(int i=1;i<prices.length;i++){
            //i天持有取决于i-1天持有和当天买入的数额（0-prices[i] 就是dp[i][0]-prices[i]）
            dp[i][0]=Math.max(dp[i-1][0],dp[i-1][1]-prices[i]);
            dp[i][1]=Math.max(dp[i-1][1],dp[i-1][0]+prices[i]);
        }
        return dp[prices.length-1][1];  //当天不持有卖出可以获得现金总是比不卖出要好
    }
}
