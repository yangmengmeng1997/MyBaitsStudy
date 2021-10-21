package com.WeeklyContest;

public class RotateGrid5798 {
    public static void main(String[] args) {
        int[][] grid={
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,16}
                };
        RotateGrid(grid);
        Print(grid);
    }
    public static void Print(int[][] grid){
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                System.out.printf("%5d ",grid[i][j]);
            }
            System.out.println();
        }
    }
    public static void RotateGrid(int[][] grid){
        //先处理i=0,第一层旋转
        int row=grid.length-1;
        int col=grid[0].length-1;
        int temp1=grid[0][0];  //存储角点左上
        int temp2=grid[0][col];  //右上
        int temp3=grid[row][0];  //左下
        int temp4=grid[row][col]; //右下
        //上面移动
        for(int j=col-1;j>0;j--){
            grid[0][j]=grid[0][j-1];
        }
        //左侧移动
        for(int i=1;i<=row-1;i++){
            grid[i+1][0]=grid[i][0];
        }
        //下侧移动
        for(int j=1;j<=col-1;j++){
            grid[row][j+1]=grid[row][j];
        }
        //右侧移动
        for(int i=row-1;i>=1;i--){
            grid[i-1][col]=grid[i][col];
        }
    }

}
