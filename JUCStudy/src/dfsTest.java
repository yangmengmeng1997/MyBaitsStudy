public class dfsTest {
    static int sum=0;
    static int[][] vis;
    public static void main(String[] args) {
        int m=3,n=2;
        vis=new int[m+1][n+1];
        dfs(1,1,m,n);
        System.out.println(sum);
    }
    public static void dfs(int m,int n,int finalx,int finaly){
        int [][]dir={{0,1},{1,0}};
        if(m>finalx || n>finaly || m<0 || n<0) return;
        if(m==finalx && n==finaly){
            sum++;
            return;
        }
        for(int i=0;i<2;i++){
            if(vis[m][n]==0){
                vis[m][n]=1;
                m=m+dir[i][0];
                n=n+dir[i][1];
                dfs(m,n,finalx,finaly);
                m=m-dir[i][0];
                n=n-dir[i][1];
                vis[m][n]=0;
            }
        }
    }
}
