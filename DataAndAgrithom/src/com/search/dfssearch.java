package com.search;

/**
 * @author ymm
 * @create 2020--11--13:25
 */
public class dfssearch {
    public static void main(String args[]){
        int a[]={0,1,2,3};
        int book[]={0,0,0,0};
        int step=1;
        int n=3;
        dfs(a,book,step,n);

    }
    public static void dfs(int a[],int book[],int step,int n)
    {
        if(step==n+1){
            for(int i=1;i<=n;i++)
            {
                System.out.println(a[i]+" ");
            }
            System.out.println();
            return ;
        }
        for(int i=1;i<=n;i++){
            if(book[i]==0)
            {
                a[step]=i;
                book[i]=1;
                dfs(a,book,step+1,n);
                book[i]=0;
            }
        }
        return ;
    }
}
