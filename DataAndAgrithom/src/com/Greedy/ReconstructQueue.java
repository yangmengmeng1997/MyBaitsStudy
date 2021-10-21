package com.Greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class ReconstructQueue {
    public static void main(String[] args){
        int[][] people= {{7,0},{4,4},{7,1},{5,0},{6,1},{5,2}};
        //System.out.println(reconstructQueue(people));
        people=reconstructQueue(people);
        System.out.println("ok");
    }
    public static int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] != o2[0]) {
                    return Integer.compare(o2[0],o1[0]);
                } else {
                    return Integer.compare(o1[1],o2[1]);
                }
            }
        });
        //队列存储的是数组
        LinkedList<int[]> que = new LinkedList<>();

        for (int[] p : people) {
            que.add(p[1],p);   //位置顺序加数据
        }

        return que.toArray(new int[people.length][]);  //需要转二维数组
    }


}
