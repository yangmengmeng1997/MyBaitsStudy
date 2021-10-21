package com.DynamicProgramming;

//时间要求严格了，暴力搜索不行了
public class UniquePaths62 {
    static int sum=0;
    public static void main(String[] args) {
        System.out.println(uniquePathsdp(1,1));
    }
    public static int uniquePaths(int m, int n) {
        int[][]map=new int[m][n];
        dfs(0,0,m,n,map);
        return sum;
    }
    //动态规划
    public static int uniquePathsdp(int m, int n){
        int[][]dp =new int[m+1][n+1];  //到（m,n）共有几种走法
        dp[1][1]=1;
        for(int i=2;i<=m;i++) dp[i][1]=1;
        for(int j=2;j<=n;j++) dp[1][j]=1;
        //初始化边界
        //开始找递推关系,可以画图推导
        for(int i=2;i<=m;i++){
            for(int j=2;j<=n;j++){
                dp[i][j]=dp[i][j-1]+dp[i-1][j];
            }
        }
        return dp[m][n];  //返回总路线数
    }
    //暴力深搜
    public static void dfs(int i,int j,int m,int n,int[][] map){
        if(i==m-1 && j==n-1) sum++;
        if(i>=m || j>=n) return ;
        int [][]dic={{0,1},{1,0}};
        for(int k=0;k<dic.length;k++){
            if(map[i][j]==0){
                map[i][j]=1;
                dfs(i+dic[k][0],j+dic[k][1],m,n,map);
                map[i][j]=0;
            }
        }
    }
}
