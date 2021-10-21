package com.Exercise;

import java.util.Scanner;

public class Exercise6 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String s=sc.nextLine();
        int num=0;
        while (sc.hasNextLine()){
            num++;
            int N=sc.nextInt();
            StringBuffer ans=new StringBuffer();
            System.out.printf("Case %d:\n",num);
            for(int i=1;i<=N;i++){   //初始切割点
                ans.append(s.substring(0,i));
                ans.append(" ");
                for(int j=i;j<s.length();j=j+N){
                    if(j+N>=s.length()){   //最后的分割点
                        ans.append(s.substring(j,s.length()));
                        break;
                    }
                    ans.append(s.substring(j,j+N));
                    ans.append(" ");
                }
                System.out.println(ans);
                ans=new StringBuffer();
        }
        }
    }
}
