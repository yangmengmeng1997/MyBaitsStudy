package com.DynamicProgramming;

public class BasePacakge {
    public static void main(String[] args) {
        mytest2();
    }
    public static void test(){
        int[] weight = {1, 3, 4};
        int[] value = {15, 20, 30};
        int bagWeight = 4; //可用背包最大容量（之后的背包容量从0-4滑动变化）
        // 二维数组
        int[][] dp=new int[weight.length + 1][bagWeight+1];

        // 初始化,倒序初始化，j表示可用空间大小，j >= weight[0] 表示可用空间数大于第一个物体重量
        for (int j = bagWeight; j >= weight[0]; j--) {
            dp[0][j] = dp[0][j - weight[0]] + value[0];
        }

        // weight数组的大小 就是物品个数
        for(int i = 1; i < weight.length; i++) { // 遍历物品
            for(int j = 0; j <= bagWeight; j++) { // 遍历背包容量
                if (j < weight[i]) dp[i][j] = dp[i - 1][j];//如果可用容量小于物体重量绝对不拿
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);//在拿和不拿中抉择最优方案
            }
        }
        System.out.println(dp[weight.length - 1][bagWeight] );
    }

    //自己仿写一遍
    public static void mytest(){
        int[] weight = {1, 3, 4};
        int[] value = {15, 20, 30};
        int bagWeight = 4; //可用背包最大容量（之后的背包容量从0-4滑动变化）
        // 二维数组
        int[][] dp=new int[weight.length + 1][bagWeight+1];
        for(int i=bagWeight;i>=weight[0];i--){
            dp[0][i]=dp[0][i-weight[0]]+value[0];
        }//初始化
        //表示物体的编号数量
        for(int i=1;i<weight.length;i++){   //遍历物体
            for(int j=0;j<=4;j++){  //遍历背包容量
                if(j<weight[i]){
                    //如果容量不够
                    dp[i][j]=dp[i-1][j];  //没有拿第i件物品，也就是说现在的价值还是之前的价值，并且背包容量也没有减少
                }
                else dp[i][j]=Math.max(dp[i-1][j],dp[i-1][j-weight[i]]+value[i]);  //抉择拿不拿第i件物品
            }
        }
        System.out.println(dp[weight.length - 1][bagWeight] );
    }

    //一维数组，感觉这样更加好理解
    public static void mytest2(){
        int[] weight = {1, 3, 4};
        int[] value = {15, 20, 30};
        int bagWeight = 4; //可用背包最大容量（之后的背包容量从0-4滑动变化）
        int[] dp=new int[bagWeight+1];  //默认初始化为0
        for(int i=0;i<weight.length;i++){
            for(int j=bagWeight;j>=weight[i];j--){  //为啥是倒序？因为这样不会重复算入同一个物体
                //保证背包容量是可以放入该物品的
                dp[j]=Math.max(dp[j],dp[j-weight[i]]+value[i]);
            }
        }
        System.out.println(dp[dp.length-1]);
    }
}
