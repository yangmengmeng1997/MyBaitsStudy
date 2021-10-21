package com.Exercise;

import java.util.Scanner;

public class Exercise7 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int num=0;
        while(sc.hasNextLine()){
            num++;
            String s1=sc.nextLine();
            String s2=sc.nextLine();
            int index=s1.indexOf(s2.charAt(0));
            if(index<0 ||(s1.length()!=s2.length())){
                System.out.printf("Case %d: No\n",num);
                continue;
            }
            String after=s1.substring(index+1,s1.length());
            String behind=s1.substring(0,index);
            behind=new StringBuffer(behind).reverse().toString();
            if(s2.contains(after) && s2.contains(behind)){
                System.out.printf("Case %d: Yes\n",num);
            }else
                System.out.printf("Case %d: No\n",num);
        }
    }
}
