package com.DynamicProgramming;

public class UniquePathsWithObstacles63 {
    public static void main(String[] args) {
        int[][] m= {{0,1},{0,0}};
        System.out.println(uniquePathsWithObstacles(m));
    }
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        //有障碍物初始化就比较麻烦了
        int[][] dp=new int[obstacleGrid.length][obstacleGrid[0].length];
        //初始化一行
        for(int i=0;i<dp[0].length;i++){
            if(obstacleGrid[0][i]==1){
                while(i<dp[0].length){
                    dp[0][i]=0;  //遇到障碍后的所有路线都不会走通
                    i++;
                }
            }else{
                dp[0][i]=1;
            }
        }
        //初始化一列
        for(int i=0;i<dp.length;i++){
            if(obstacleGrid[i][0]==1){
                while(i<dp.length){
                    dp[i][0]=0;  //遇到障碍后的所有路线都不会走通
                    i++;
                }
            }else{
                dp[i][0]=1;
            }
        }
        //一样的转移方程
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(obstacleGrid[i][j]==1){
                    dp[i][j]=0;  //遇到障碍此路不通
                }else
                    dp[i][j]=dp[i][j-1]+dp[i-1][j];
            }
        }
        return dp[dp.length-1][dp[0].length-1];
    }
}
