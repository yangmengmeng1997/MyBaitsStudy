package com.Greedy;

import java.util.Arrays;
import java.util.Comparator;

public class FindMinArrowShots {
    public static void main(String[] args) {
        int[][] points={{9,12},{1,10},{4,11},{8,12},{3,9},{6,9},{6,7}};
        System.out.println(findMinArrowShots(points));
    }
    public static int findMinArrowShots(int[][] points) {
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]!=o2[0]){
                    return Integer.compare(o1[0],o2[0]);
                }else
                    return Integer.compare(o1[1],o2[1]);
            }
        });  //先排序，这种排序需要多写
        int start=points[0][0];
        int end=points[0][1];
        int ans=1;
        for(int i=1;i<points.length;i++){
            if(end<points[i][0]){  //交集边界小于起始点
                ans++;
                start=points[i][0];
                end=points[i][1];
                continue;
            }
            //求交集，起点变化，终点变化
            start=Math.max(start,points[i][0]);
            end=Math.min(end,points[i][1]);
        }
        return ans;
    }
}
