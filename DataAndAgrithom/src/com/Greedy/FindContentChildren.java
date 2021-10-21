package com.Greedy;

import java.util.Arrays;

public class FindContentChildren {
    public static void main(String[] args){
        int []g={1,2};
        int []s={1,2,3};
        System.out.println(findContentChildren(g,s));
    }
    public static int findContentChildren(int[] g, int[] s) {
        int sum=0;
        Arrays.sort(g);
        Arrays.sort(s);  //排序
        for(int i=0,j=0;i<s.length || j<g.length;){
            if(i==s.length ||j==g.length) return sum;
            if(s[i]>=g[j]){  //饼干够分配
                sum++;
                i++;
                j++;
            }
            else{   //饼干不够分配就拿下一块饼干试试
                i++;
            }
        }
        return sum;
    }
}
