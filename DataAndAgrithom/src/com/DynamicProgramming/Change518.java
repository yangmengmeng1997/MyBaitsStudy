package com.DynamicProgramming;

public class Change518 {
    public static void main(String[] args) {
        int[] nums={1, 2, 5};
        System.out.println(change(5,nums));
    }
    public static int change(int amount, int[] coins) {
        int []dp=new int[amount+1];  //取面值为i有多少种方法
        dp[0]=1;  //初始化
        //调试发现跌倒循环就是重复计算 3=1+2 3=2+1算作俩种，差异较大，适合排列问题
//        for(int j=0;j<=amount;j++) {
//            for (int i = 0 ; i<coins.length; i++) {
//                if(j>=coins[i])
//                    dp[j] = dp[j] + dp[j - coins[i]]; //只有物体0的方法数加上新来硬币的方法数
//            }
//            System.out.println(Arrays.toString(dp));
//        }
        //先放物体再循环背包容量，就是可以求组合问题
        for(int i=0;i<coins.length;i++)
            for(int j=coins[i];j<=amount;j++){
                dp[j]+=dp[j-coins[i]];
            }
        return dp[amount];
    }
}
