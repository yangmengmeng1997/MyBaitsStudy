package com.DFSExercise;

public class MaxAreaOfIsland695 {
    static int area=0;
    static int maxarea=0;
    public static void main(String[] args) {
        int[][] grid={{0,0,1,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,0,0,1,0,1,0,0},
                {0,1,0,0,1,1,0,0,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,1,1,0,0,0,0}};
        System.out.println(maxAreaOfIsland(grid));
    }
    public static int maxAreaOfIsland(int[][] grid) {
        boolean[][] visible=new boolean[grid.length][grid[0].length];
        for(int i=0;i<grid.length;i++)
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j]==1){
                    area=0;
                    dfs(grid,i,j,visible);
                    if(area>maxarea)
                        maxarea=area;
                }
            }
        return maxarea;
    }
    //统计一块联通岛屿的面积
    public static void dfs(int[][] grid,int x,int y,boolean[][]visible){
        if(!isValid(grid,x,y)) return;
        if(grid[x][y]==0 || visible[x][y]) return;
        visible[x][y]=true;
        area+=1;
        int[][] dict={{1,0},{-1,0},{0,1},{0,-1}};
        for(int i=0;i<dict.length;i++){
            dfs(grid,x+dict[i][0],y+dict[i][1],visible);
        }
    }
    public static boolean isValid(int[][]grid,int x,int y){
        if(x<0 || x>=grid.length || y<0 ||y>=grid[0].length) return false;
        return true;
    }

}
