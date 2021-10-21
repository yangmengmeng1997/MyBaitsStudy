package com.DynamicProgramming;

public class CoinChange322 {
    public static void main(String[] args) {
        int[] coins={2,5,1};
        System.out.println(coinChange(coins,11));
    }
    public static int coinChange(int[] coins, int amount) {
        if(amount==0) return 0;
        int[] dp=new int[amount+1];
        dp[0]=0;
        for(int i=1;i<=amount;i++)
            dp[i]=Integer.MAX_VALUE;   //最大值初始化
        for(int i=0;i<coins.length;i++)
            for(int j = coins[i]; j<=amount; j++){
                //不为初始值才计算，不然没有意义，例如数组为2，但是要兑换3元
                if(dp[j-coins[i]]!=Integer.MAX_VALUE){
                    dp[j]=Math.min(dp[j],dp[j-coins[i]]+1);
                }
            }
        if(dp[amount]==Integer.MAX_VALUE) return -1;
        else return dp[amount];
    }
}
