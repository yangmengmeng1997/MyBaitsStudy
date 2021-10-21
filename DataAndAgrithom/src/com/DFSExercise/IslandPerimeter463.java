package com.DFSExercise;

public class IslandPerimeter463 {
    static int sum=0;
    public static void main(String[] args) {
        int[][]grid={
                {0,1,0,0},
                {1,1,1,0},
                {0,1,0,0},
                {1,1,0,0}};
        System.out.println(islandPerimeter(grid));
    }
    public static int islandPerimeter(int[][] grid) {
        boolean[][] visible=new boolean[grid.length][grid[0].length];
        for(int i=0;i<grid.length;i++)
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j]==1)
                    dfs(grid,i,j,visible);
            }
        return sum;
    }
    public static void dfs(int[][] grid,int x,int y,boolean[][] visible){
        if(!isValid(grid,x,y)) return;
        if(grid[x][y]==0) return;
        if(visible[x][y]) return;
        int[][] dir={{1,0},{-1,0},{0,1},{0,-1}};
        visible[x][y]=true;
        int length=4;
        //对这格子的周围进行试探访问,求出边界长度
        for(int i=0;i<dir.length;i++){
            if(isValid(grid,x+dir[i][0],y+dir[i][1]) && grid[x+dir[i][0]][y+dir[i][1]]==1)
                length--;
        }
        //累加有效长度
        sum+=length;
        for(int i=0;i<dir.length;i++){
            dfs(grid,x+dir[i][0],y+dir[i][1],visible);
        }
    }
    public static boolean isValid(int[][] grid,int x,int y){
        if(x<0 || x>=grid.length || y<0 || y>=grid[0].length) return false;
        else return true;
    }
    
}
