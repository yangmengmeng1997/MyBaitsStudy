package com.search;

/**
 * @author ymm
 * @create 2020--11--19:29
 */
public class LinearSearch {
    public static void main(String []args){
        int array[]={1,9,11,-1,34,89};
        int index=seqsearch(array,-1);
        if(index>=0)
            System.out.println("找到结果，下标为"+index);
        else
            System.out.println("找不到序列");
    }
    //有序无序才可以
    public static int seqsearch(int arr[],int val) {
        //直接逐一查找，找到返回下标，否则返回下标为-1
        for (int i = 0; i < arr.length; i++) {
            if (val == arr[i])
                return i;
        }
        return -1;
    }
}
