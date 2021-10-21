package com.DFSExercise;

public class NumIslands200 {
    public static void main(String[] args) {
        char[][] grid={
                {'1','1','1'},
                {'0','1','0'},
                {'1','1','1'},
        };
        System.out.println(numIslands(grid));
    }
    public static int numIslands(char[][] grid) {
        int sum=0;
        boolean[][] visible=new boolean[grid.length][grid[0].length];
        for(int i=0;i<grid.length;i++)
            for(int j=0;j<grid[0].length;j++){
                if(!visible[i][j] && grid[i][j]=='1'){
                    sum++;  //发现孤立的岛屿
                    bt(grid,i,j,visible);   //将相连的陆地遍历完毕为止叫做一个岛屿
                }
            }
        return sum;
    }
    //dfs遍历求连接的岛屿（单个）
    public static void dfs(char[][] grid,int x,int y,boolean[][] visible){
//        if(!isValid(grid,x,y)) return;  //判断这次是否合法
//        if(grid[x][y]!='1') return;  //遍历到海水也是直接结束函数
        int[][] dir={{1,0},{-1,0},{0,1},{0,-1}};  //定义遍历的方向，分别是向右，向左，向上，向下
        for(int i=0;i<4;i++){
            //没有访问过并且是陆地才染色
            x=x+dir[i][0];
            y=y+dir[i][1];   //先试探再判断行不行,必须先判断是否越界，&& 具有阻断作用
            if((isValid(grid,x,y)) && !visible[x][y] &&grid[x][y]=='1'){
                visible[x][y]=true;
                dfs(grid,x,y,visible);
            }//如果不满足条件就回溯到之前状态继续长尝试其他的状态
            x=x-dir[i][0];
            y=y-dir[i][1]; //回溯回去最开始状态
        }
    }

    //简化一下
    public static void bt(char[][] grid,int x,int y,boolean[][] visible){
        if(!isValid(grid,x,y)) return;
        if(grid[x][y]!='1') return;
        if(visible[x][y]==true) return;
        //越界或者非陆地或者已经当问过的情况都已经解决完毕，接下来这种情况必然是合法陆地并且没有被访问过的情况了。
        visible[x][y]=true;
        int[][] dir={{1,0},{-1,0},{0,1},{0,-1}};  //定义遍历的方向，分别是向右，向左，向上，向下
        for(int i=0;i<dir.length;i++){  //遍历四个方位，遍历所有可能
            bt(grid,x+dir[i][0],y+dir[i][1],visible);
        }
    }
    public static boolean isValid(char[][] grid,int x,int y){
        if(x>=grid.length || y>=grid[0].length || x<0 || y<0) return false;  //超出边界return
        return true;
    }
}
