/**
 * @author ymm
 * @create 2020--10--15:20
 * 递归走迷宫，取得最短路径
 * 1表示墙，0表示未走过，2 表示走过，3表示这个位置不通
 */
public class Maze{
    public static void main(String[] args)
    {
        //定义迷宫地图,定义墙
        int [][]maze = new int[8][7];
        for(int i=0;i<8;i++)
        {
            maze[i][0]=1;
            maze[i][6]=1;
        }
        for (int j=0;j<7;j++)
        {
            maze[0][j]=1;
            maze[7][j]=1;
        }
        //设置障碍

        maze[3][1]=maze[3][2]=1;
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<7;j++)
                System.out.print(maze[i][j]);
            System.out.println();
        }
        findway(maze,1,1);
        System.out.println("找到路后");
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<7;j++)
                System.out.print(maze[i][j]);
            System.out.println();
        }
    }

    //递归走路,从（1，1）出发，到（6，5）结束,定义方向下，右，上，左走迷宫
    public  static boolean findway(int [][]map,int i,int j)
    {
        if(map[6][5]==2)
            return true;   //递归出口
        else
        {
            if(map[i][j]==0)  //如果当前位置没有走过
            {
                map[i][j]=2;  //假定当前点可以走通
                if(findway(map,i+1,j))  //下走
                    return  true;
                else if(findway(map,i,j+1))  //右走
                    return true;
                else if(findway(map,i-1,j))  //上走
                    return true;
                else if(findway(map,i,j-1))  //左走
                    return true;
                else
                {
                    map[i][j]=3;   //四个方向都试过，无效，此点不通
                    return false;
                }
            }
            else{  //当前位置不是0，可能为1，2，3,都无效，不可以再递归了
                return false;
            }
        }
        
    }

}
