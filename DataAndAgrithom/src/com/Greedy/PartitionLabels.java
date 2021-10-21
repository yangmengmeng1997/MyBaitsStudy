package com.Greedy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PartitionLabels {
    static List<Integer> res=new ArrayList<>();
    public static void main(String[] args) {
        String s="ababcbacadefegdehijhklij";
        res=partitionLabels(s);
        System.out.println(res);
    }
    public static List<Integer> partitionLabels(String S) {
        List<Integer> list = new LinkedList<>();
        int[] edge = new int[123];
        char[] chars = S.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            edge[chars[i] - 0] = i;
        }
        int idx = 0;
        int last = -1;
        for (int i = 0; i < chars.length; i++) {
            idx = Math.max(idx,edge[chars[i] - 0]);
            if (i == idx) {
                list.add(i - last);
                last = i;
            }
        }
        return list;
    }
}
