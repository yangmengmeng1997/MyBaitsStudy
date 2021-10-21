package com.DFSExercise;

import java.util.ArrayList;
import java.util.List;

public class Partition {
    static List<String> temp=new ArrayList<>();
    static List<List<String>> res=new ArrayList<>();
    public static void main(String[] args){
        String s="aab";
        partition(s);
        System.out.println(res);
    }
    public static List<List<String>> partition(String s) {
        backtracking(s,0);
        return res;
    }
    public static void backtracking(String s,int startindex){
        if(startindex==s.length()){
            List<String> ans=new ArrayList<>(temp);
            res.add(ans);
            return;
        }
        for(int i=startindex;i<s.length();i++){
            String mysubstr=s.substring(startindex,i+1);  //取字符串，i需要+1，不然有空字符串
            if(isPalindromicString(mysubstr)){   //分隔的是回文子串就继续下去不然就终止for循环
                temp.add(mysubstr);
                backtracking(s,i+1);
                temp.remove(mysubstr);
            }else
                continue;
        }
    }
    //辅助函数判断回文字符串
    public static boolean isPalindromicString(String s){
        for(int i=0,j=s.length()-1;i<=j;i++,j--){
            if(s.charAt(i)==s.charAt(j)){
                continue;
            }else
                return false;
        }
        return true;
    }
}

