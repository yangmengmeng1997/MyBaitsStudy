package com.WeeklyContest;

public class maxPoints5815 {
    public static void main(String[] args) {
        int[][] points={
                {0,3,0,4,2},
                {5,4,2,4,1},{5,0,0,5,1},{2,0,1,0,3}
        };
        System.out.println(maxPoints(points));
    }
    //这种会超时吧，想法太简单错误
    public static long maxPoints(int[][] points) {
        int m=points.length;
        int n=points[0].length;
        long maxscore=0;   //记录累加最大值得分
        int[] maxcol=new int[m];   //记录每一行的最大列的位置
        for(int i=0;i<n;i++){
            if(maxscore<points[0][i]) {
                maxcol[0] = i;  //第一个最大列
                maxscore = points[0][i];
            }
        }
        for(int i=1;i<m;i++){   //遍历每一行
            long max=0;   //记录当前行的最大得分
            for(int j=0;j<n;j++){  //遍历每一列
                for(int k=0;k<n;k++){   //逐个比较
                    int temp=points[i][j]+points[i-1][k]-Math.abs(j-k);
                    if(max<temp){
                        max=temp;
                        maxcol[i]=j;
                    }
                }
            }  //遍历所有行找到最优位置
        }
        for(int i=1;i<maxcol.length;i++){
            maxscore+=(points[i][maxcol[i]]-Math.abs(maxcol[i]-maxcol[i-1]));
        }
        return maxscore;
    }
}
