package com.DynamicProgramming;

public class MaxProfit188 {
    public static void main(String[] args) {
        int[] prices = {};
        int k=2;
        System.out.println(maxProfit(k,prices));
    }
    //交易次数限定，不确定，仿照123的思想
    public static int maxProfit(int k, int[] prices) {
        int[][] dp=new int[prices.length][2*k+1];
        dp[0][0]=0;
        for (int i=1;i<2*k+1;i++){
            if(i%2==1) dp[0][i]=-prices[0];  //初始化卖出的价格是负的。
        }
        for(int i=1;i<prices.length;i++){
            for(int j=1;j<2*k+1;j++){
                //奇数天买入两个状态
                //啥也不做，就是i-1天的状态，i天买入扣除票价
                if(j%2==1) {
                    dp[i][j]=Math.max(dp[i-1][j],dp[i-1][j-1]-prices[i]);
                }else{
                    //偶数状态，卖出票价
                    //两个状态：i-1天的状态，以及i-1天买入状态基础上才可以卖出，
                    //那么持有现金会加上原有的金额
                    dp[i][j]=Math.max(dp[i-1][j],dp[i-1][j-1]+prices[i]);
                }
            }
        }
        return dp[prices.length-1][2*k];  //返回最后的答案，一定是最后一次卖出获得利润最大
    }
}
