package com.DFSExercise;

import java.util.ArrayList;
import java.util.List;

public class Combine {
    static List<List<Integer>> res=new ArrayList<>();
    static List<Integer> temp=new ArrayList<>();

    public static void main(String[] args){
        combine(4,3);
        System.out.println(res);
    }
    public static List<List<Integer>> combine(int n, int k) {
        backtracking(n,k,1);
        return res;
    }
    public static void backtracking(int n,int k,int startindex){
        if(temp.size()==k){ //只收集特定长度的子序列
            List<Integer> ans=new ArrayList<>(temp);
            res.add(ans);
            return;
        }//终止条件
        for(int i=startindex;i<=n-(k-temp.size())+1;i++){
            temp.add(i);
            backtracking(n,k,i+1);
            temp.remove(new Integer(i));  //移除下标
        }
    }
}
