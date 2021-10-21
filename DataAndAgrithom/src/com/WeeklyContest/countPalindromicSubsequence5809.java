package com.WeeklyContest;

import java.util.LinkedList;

//TLE
public class countPalindromicSubsequence5809 {
    static LinkedList<String> path=new LinkedList<>();
    static int num=0;
    public static void main(String[] args) {
        String s="bbcbaba";
        countPalindromicSubsequence(s);
    }
    public static int countPalindromicSubsequence(String s) {
        StringBuffer temp=new StringBuffer();
        dfs(s,0,temp);
        System.out.println(path);
        return 0;
    }
    //这里的step可以表示的每一层的起始搜索位置，如果dfs（s,step+1) 那么这一层的起始位置就比变化了
    //dfs(s,i+1）表示就是从i出发开始的递增，还是debug吧调试吧
    public static void dfs(String s,int step,StringBuffer temp){
        if(temp.length()==3){
            if(check(temp.toString(),path)){
                num++;
                path.add(temp.toString());
            }
        }
        for(int i=step;i<s.length();i++){
                temp.append(Character.toString(s.charAt(i)));
                dfs(s,i+1,temp);   //为什么是i+1，和step+1有什么区别
                temp.deleteCharAt(temp.length()-1);
            }
        }
        public static boolean check(String s,LinkedList<String> res){
           if(res.contains(s)) return false;
           else return s.charAt(0)==s.charAt(2);
        }
    }
