package com.Exercise;

import java.util.*;

public class Exercise5 {
    static List<List<String>> lists = new ArrayList<>();
    static Deque<String> deque = new LinkedList<>();
    public static void main(String[] args) {
        partition("abcde",2);
        System.out.println(lists);
    }
    public static List<List<String>> partition(String s,int N) {
        backTracking(s, 0,N);
        return lists;
    }

    public static void backTracking(String s, int startIndex,int N) {
        //如果起始位置大于s的大小，说明找到了一组分割方案
        if (startIndex >= s.length()) {
            lists.add(new ArrayList(deque));
            return;
        }
        for (int i = startIndex; i < s.length(); i++) {
            //如果满足长度要求，则记录下来
            if (isValid(s,N,startIndex, i)) {
                String str = s.substring(startIndex, i + 1);
                deque.addLast(str);
            } else {
                continue;
            }
            //起始位置后移，保证不重复
            backTracking(s, i + 1,N);
            deque.removeLast();
        }
    }
    //判断长度是否足够
    public static boolean isValid(String s,int N, int startIndex, int end) {
        return (end-startIndex<=N);
    }
}
