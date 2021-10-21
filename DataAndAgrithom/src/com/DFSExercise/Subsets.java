package com.DFSExercise;

import java.util.ArrayList;
import java.util.List;

//这个还要进行剪枝，不是全排列了，想用的元素顺序不一样是同一个序列
//还是错误，没有搞明白回溯剪枝
public class Subsets {
    static List<List<Integer>> res=new ArrayList<>();
    static List<Integer> temp=new ArrayList<>();
    static boolean[] visible=new boolean[100];
    public static void main(String[] args){
        int []nums={1,2,3};
        process(nums,0,0);
        System.out.println(res);
    }
    public static List<List<Integer>> subsets(int[] nums) {
        process(nums,0,0);
        return res;
    }
    public static void process(int[] nums,int index,int length){
        if(index==length){
            res.add(new ArrayList<>(temp));
        }
        //全排列每次都是从0还是，而这里是从index开始！！！！
        for(int i=index;i<nums.length;i++){ //注意这里的i不是从0还是，而是从index开始，这是为了避免重复，这里是关键
            if(!visible[i]){
                visible[i]=true;
                temp.add(nums[i]);
                process(nums,index+1,length+1);
                visible[i]=false;
                temp.remove(temp.size()-1);
            }
        }
    }
}
