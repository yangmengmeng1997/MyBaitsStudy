package com.DFSExercise;

import java.util.ArrayList;
import java.util.List;

public class Subsets2 {
    static List<List<Integer>> res=new ArrayList<>();
    static List<Integer> temp=new ArrayList<>();
    public static void main(String[] args){
        int []nums={1,2,3};
        subsets(nums);
        System.out.println(res);
    }
    public static List<List<Integer>> subsets(int[] nums){
        backtracking(nums,0);
        return res;
    }
    public static void backtracking(int []nums,int startindex){
        //刚开始加入是为了加入[]这个序列
        res.add(new ArrayList<>(temp));   //需要加入整个路径而不只是叶子节点，所以是无条件添加
        if(startindex>=nums.length) return;
        for(int i=startindex;i<nums.length;i++){
            temp.add(nums[i]);
            backtracking(nums,i+1);
            temp.remove(new Integer(nums[i]));
        }
    }

}
