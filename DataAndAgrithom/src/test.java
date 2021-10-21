/**
 * @author ymm
 * @create 2021--01--10:56
 */
public class test {
    public static void main(String []args){
        int ans=0;
         for(int i=1;i<=3;i++)
             for(int j=1;j<=3 ;j++)
                 for(int k=1;k<=3 ;k++)
                     for(int m=1;m<=3 ;m++)
                     {
                         if(i<=j && i<=k && i<=m && j<=k && j<=m && k<=m){
                             System.out.printf("排列为 %d %d %d %d\n",i,j,k,m);
                             ans++;
                         }
                     }
         System.out.printf("总计有%d\n",ans);
    }
}
