package com.DynamicProgramming;

public class MaxProfit123 {
    public static void main(String[] args) {
        int[] prices = {7,6,4,3,1};
        System.out.println(maxProfit(prices));
    }
    public static int maxProfit(int[] prices) {
        int [][]dp=new int[prices.length][5];
        //5种状态：0, 不操作，1：第一次买入；2.第一次卖出；3，第二次买入；4.第二次卖出
        //初始化，除了这个买入操作其他都初始化为0
        dp[0][1]=-prices[0];
        dp[0][3]=-prices[0];
        for(int i=1;i<prices.length;i++){
            dp[i][1]=Math.max(dp[i-1][1],dp[i-1][0]-prices[i]);
            dp[i][2]=Math.max(dp[i-1][2],dp[i-1][1]+prices[i]);
            //第二次买入在第一次卖出的基础上进行计算
            dp[i][3]=Math.max(dp[i-1][3],dp[i-1][2]-prices[i]);
            dp[i][4]=Math.max(dp[i-1][4],dp[i-1][3]+prices[i]);
        }
        return dp[prices.length-1][4];  //返回最大利润，必然是第二次卖出之后获得的利润最大
    }
}
