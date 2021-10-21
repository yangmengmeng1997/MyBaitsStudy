package com.DFSExercise;

import java.util.ArrayList;
import java.util.List;

public class Permute {
    static boolean visited[]=new boolean[100];
    static List<List<Integer>> res=new ArrayList<>();
    static List<Integer> temp=new ArrayList<>();
    public static void main(String[] args){
        int[] nums={1,2,3};
        Process(nums);
        System.out.println(res);
    }
    public static List<List<Integer>> permute(int[] nums) {
        Process(nums);
        return res;
    }
    public static void Process(int []nums){
        if(temp.size()==nums.length)
        {
            //这一步不知道语法，可以这样对list进行拷贝的，并且不损耗原来的temp数组便于之后的递归
            res.add(new ArrayList<>(temp));
            return;
        }
        for(int j=0;j<nums.length;j++) {
            if (!visited[j]) {
                visited[j] = true;
                temp.add(nums[j]);
                Process(nums);
                //回溯，要将节点的访问标记复原，并且需要将list列表给复原（即移除数据），即和原来的操作相反的操作就是回溯
                visited[j] = false;
                temp.remove(new Integer(nums[j]));  //将最后的元素移除
            }
        }
    }
}
