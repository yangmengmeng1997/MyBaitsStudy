package com.Greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Merge {
    //int[] 是对象，是一种数据类型
    static List<int[]> res=new ArrayList<>();
    public static void main(String[] args) {
        int[][] intervals={{1,4},{2,3}};
        intervals=merge(intervals);
        for(int i=0;i<intervals.length;i++){
            System.out.println(Arrays.toString(intervals[i]));
        }
    }
    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]==o2[0])
                    return Integer.compare(o1[1],o2[1]);
                else
                    return Integer.compare(o1[0],o2[0]);
            } //先按结束坐标排序
        });
        int start=intervals[0][0];
        int end=intervals[0][1];
        for(int i=1;i<intervals.length;i++){
            if(intervals[i][0]>end){
                res.add(new int[]{start,end});
                start=intervals[i][0];
                end=intervals[i][1];   //无重叠位置
            }else{  //有重叠位置，此时start排序后最小的再前面
                //重叠时组要判断一下是不是区间子集的情况
                end=Math.max(end,intervals[i][1]); //更新end即可
            }
        }
        res.add(new int[]{start,end});
        return res.toArray(new int[res.size()][]);
    }
}
