package com.DFSExercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permute2 {
    static List<Integer> temp=new ArrayList<>();
    static List<List<Integer>> res=new ArrayList<>();
    static int []visited=new int[100];  //一个数组标记是否访问过
    //static int[]used=new int[100];   //一个数据标记是否去重
    public static void main(String[] args){
        int []nums={2,2,1,1};
        permuteUnique(nums);
        System.out.println(res);
    }
    public static List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        backtracking(nums);
        return res;
    }
    public static void backtracking(int []nums){
        if(temp.size()==nums.length){
            res.add(new ArrayList<>(temp));
            return;
        }
        for(int i=0;i<nums.length;i++){
            //去重
            if(i>0 && nums[i]==nums[i-1] && visited[i-1]==0){
                continue;
            } //对未标记的点访问
            if(visited[i]==0){
                visited[i]=1;
                temp.add(nums[i]);
                backtracking(nums);
                visited[i]=0;
                temp.remove(temp.size()-1);
            }else
                continue;
        }
    }
}
