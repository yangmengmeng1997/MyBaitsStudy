package com.WeeklyContest;

import java.util.HashSet;

public class countPalindromicSubsequence5809_2 {
    //dfs暴力超时
    public static void main(String[] args) {
        String s="bbcbaba";
        System.out.println(countPalindromicSubsequence(s));
    }
    //只能说是动脑子,想不到
    public static int countPalindromicSubsequence(String s){
        //遍历字符串中的每一个字符，希望找到重复的字符
        int res=0;
        int n=s.length();
        for(char ch='a';ch<='z';ch++){   //遍历26个字母，这种循环次数是有限的
            int l=0;    //找到最左
            int r=n-1;  //找到最右
            while(l<n && s.charAt(l)!=ch) l++;  //找到回文串的左边字母
            while(r>=0 && s.charAt(r)!=ch) r--; //找到回文串的右边字母
            if(r-l<2) continue;   //构成不了回文串
            HashSet<Character> myset=new HashSet<Character>();
            for(int i=l+1;i<r;i++){
                myset.add(s.charAt(i));
            }
            res+=myset.size();
        }
        return res;
    }
}
