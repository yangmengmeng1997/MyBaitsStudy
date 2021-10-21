package com.DFSExercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {
    static int sum=0;
    static List<Integer> temp=new ArrayList<>();
    static List<List<Integer>> res=new ArrayList<>();
    static int[] used=new int[100];
    public static void main(String[] args){
        int[] candidates={10,1,2,7,6,1,5};
        int target=8;
        Arrays.sort(candidates);  //排序才可以进行剪枝优化，否则会漏
        combinationSum(candidates,target);
        System.out.println(res);
    }
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        backtracking(candidates,target,0);
        return res;
    }
    public static void backtracking(int[]candidates,int target,int startindex){
        if(sum>target) return;
        if(sum==target){
            List<Integer> ans=new ArrayList<>(temp);
            res.add(ans);
        }
        for(int i=startindex;i<candidates.length && sum+candidates[i]<=target ;i++){
            //为什么是0啊，好好想想，这里当第一个i遍完毕所有结果后悔回溯使得used重新变为0
            //因为用的是startindex使得只会从当前位置之后搜索
            // used[i - 1] == true，说明同一树支candidates[i - 1]使用过
            // used[i - 1] == false，说明同一树层candidates[i - 1]使用过
            if(i>0 && candidates[i]==candidates[i-1] && used[i-1]==0) {
                continue;
            }
                sum=sum+candidates[i];
                temp.add(candidates[i]);
                used[i]=1;
                backtracking(candidates,target,i+1);  //保证取重复的元素
                used[i]=0;
                sum=sum-candidates[i];
                temp.remove(new Integer(candidates[i]));
        }
    }
}
