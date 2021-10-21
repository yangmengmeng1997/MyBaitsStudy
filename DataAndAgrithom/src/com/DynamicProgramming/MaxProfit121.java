package com.DynamicProgramming;

public class MaxProfit121 {
    public static void main(String[] args) {
        int[] prices={7,1,5,3,6,4};
        System.out.println(maxProfit(prices));
    }
    //不是用了dp数组就是动态规划了，这只能是一个递推而已
    public static int maxProfit(int[] prices) {
        int minprice = prices[0];
        int[] dp=new int[prices.length];
        dp[0]=0;
        for(int i=1;i<prices.length;i++){
            //转移方程：第i天的最大利润为
            //啥也不操作就是第i-1天的最大利润，以及当前最大值卖出的价格
            dp[i]=Math.max(dp[i-1],prices[i]-minprice);
            if(minprice>prices[i]) minprice=prices[i];  //更新最小值
        }
        return dp[prices.length-1];
    }

    //真正的数组递推
    public static int maxProfit1(int[] prices){
        int[][] dp=new int[prices.length][2];  //0 表示持有股票,1表示不持有股票
        dp[0][0]=-prices[0];
        dp[0][1]=0;
        for(int i=1;i<prices.length;i++){
            //i天持有取决于i-1天持有和当天买入的数额（0-prices[i] 就是dp[i][0]-prices[i]）
            dp[i][0]=Math.max(dp[i-1][0],-prices[i]);
            dp[i][1]=Math.max(dp[i-1][1],dp[i-1][0]+prices[i]);
        }
        return dp[prices.length-1][1];  //当天不持有卖出可以获得现金总是比不卖出要好
    }
}
