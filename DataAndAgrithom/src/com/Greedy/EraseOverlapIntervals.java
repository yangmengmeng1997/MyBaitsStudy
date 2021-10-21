package com.Greedy;

import java.util.Arrays;
import java.util.Comparator;

public class EraseOverlapIntervals {
    public static void main(String[] args) {
        int[][] intervals={{1,100},{11,22},{1,11},{2,12}};
        System.out.println(eraseOverlapIntervals(intervals));
    }
    public static int eraseOverlapIntervals(int[][] intervals) {
        int sum=0;
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[1],o2[1]);  //按照右边界排序
            }
        });
        int end=intervals[0][1];
        for(int i=1;i<intervals.length;i++){
            if(intervals[i][0]>=end){
                end=intervals[i][1];
                continue;
            }else
                sum++;
        }
        return sum;
    }
}
