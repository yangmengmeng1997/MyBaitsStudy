package com.DFSExercise;

public class FloodFill {
    static int visit[][] ={{0,0,0},{0,0,0},{0,0,0}};
    public static void main(String[] args){
        int [][]image={{1,1,1},{1,1,0},{1,0,1}};
        int sr = 1;
        int sc = 1;
        int newColor = 2;
        int initcolor=image[sr][sc];
        visit[sr][sc]=1;   //初始化标记为已访问
        floodFill(image,sr,sc,newColor,initcolor);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(image[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static void floodFill(int[][] image, int sr, int sc, int newColor,int initcolor){
        int [][]dir={{1,0},{-1,0},{0,1},{0,-1}};
        for(int i=0;i<4;i++){   //循环遍历四个方向
            //判断有没有染过色以及有没有超出边界
            int tempx = sr+dir[i][0];
            int tempy = sc+dir[i][1];
            if(tempx>=0 && tempx<image.length && tempy<image[0].length && tempy>=0 && visit[tempx][tempy]==0){
                if(image[tempx][tempy]==initcolor){
                    image[tempx][tempy]=newColor;  //符合条件就标记+染色
                    visit[tempx][tempy]=1;
                    floodFill(image,sr+dir[i][0],sc+dir[i][1],newColor,initcolor);   //递归上色
                }
                else continue;
            }
        }
    }
}
