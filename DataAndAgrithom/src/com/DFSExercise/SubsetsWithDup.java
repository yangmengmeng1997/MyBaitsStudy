package com.DFSExercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubsetsWithDup {
    static List<Integer> temp=new ArrayList<>();
    static List<List<Integer>> res=new ArrayList<>();
    static boolean []used=new boolean[100];
    public static void main(String[] args){
        int []nums={1,2,2};
        Arrays.sort(nums);
        subsetsWithDup(nums);
        System.out.println(res);
    }
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        backtracking(nums,0);
        return res;
    }
    public static void backtracking(int[] nums,int startindex){
        res.add(new ArrayList<>(temp));  //拷贝结果
        if(startindex==nums.length) return;
        for(int i=startindex;i<nums.length;i++){
            //关键去重
            if(i>0 && nums[i]==nums[i-1] && used[i-1]==false){
                continue;
            }
            temp.add(nums[i]);
            used[i]=true;
            backtracking(nums,i+1);
            temp.remove(new Integer(nums[i]));
            used[i]=false;
        }
    }

}
