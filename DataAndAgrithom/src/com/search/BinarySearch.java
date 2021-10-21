package com.search;

import java.util.ArrayList;

/**
 * @author ymm
 * @create 2020--11--19:43
 */

//二分查找必然有序
public class BinarySearch {
    public static void main(String []args){
        int array[]={1,2,3,4,5,6,7,8,9,10};

//        int index =binarysearch(array,0, array.length-1, 123);
//        if(index>=0)
//            System.out.println("找到，下标为"+index+"，数字为"+array[index]);
//        else System.out.println("找不到待查找的数字");
//        ArrayList<Integer> list=new ArrayList<Integer>();
//        list=binarysearchgai(array,0, array.length-1,100);//小bug是不能排序，下标混乱
//        if(list.size()>0)
//        {
//            System.out.println("查找结果为"+list);
//        }
//        else {
//            System.out.println("找不到待查找结果");
//        }
        int index=insertsearch(array,0,9,5);
        if(index>=0){
            System.out.println(index);
        }
        else
            System.out.println("没找到序列");


    }

    //二分查找
    public static int binarysearch(int arr[],int left,int right,int val)
    {
        int mid=(left+right)/2;
        int midval=arr[mid];
        if(left<=right) {
            if (midval > val) {
                return binarysearch(arr, left, mid - 1, val);
            } else if (midval < val) {
                return binarysearch(arr, mid + 1, right, val);
            } else return mid;
        }
        else return -1;
    }

    /*
      1.找到mid不用返回，从mid左右扫描，直到不一样为止，将所有的下标返回list集合中即可
     */
    public static ArrayList<Integer> binarysearchgai(int arr[], int left, int right, int val)
    {
        int mid=(left+right)/2;
        int midval=arr[mid];
        if(left<=right) {
            if (midval > val) {
                return binarysearchgai(arr, left, mid - 1, val);
            } else if (midval < val) {
                return binarysearchgai(arr, mid + 1, right, val);
            } else {
                ArrayList<Integer> Indexlist= new ArrayList<Integer>();
                int temp=mid-1;   //左边第一个值
                while(true)//说明找到头了或者左边检测到没有重复的数字了
                {
                    if(temp<0 || arr[temp]!=val) {
                        break;   //退出
                    }
                    //否则就将temp放入集合中
                    Indexlist.add(temp);
                    temp--;  //继续左移
                }
                Indexlist.add(mid);
                temp=mid+1;  //右边第一个值
                while(true)//说明找到头了或者左边检测到没有重复的数字了
                {
                    if(temp>=arr.length || arr[temp]!=val) {
                        break;   //退出
                    }
                    //否则就将temp放入集合中
                    Indexlist.add(temp);
                    temp++;  //继续右移
                }
                return Indexlist;
            }
        }
        else return new ArrayList<Integer>();
    }

    //针对分布均匀的数据，要查找的数据在两端，折半查找还是比较耗时的。所以有了插值查找，其实就是数学公式变形
    //分布不均匀的数据不一定比二分查找好
    public static int insertsearch(int arr[],int left,int right,int val){
        //查找范围越界或者小于最小值或者大于最大值都可以提前结束，前提是数组必须有序才会查找
        //注意val<arr[0] || val>arr[arr.length-1]这两句话不是可有可无的，必须要有，否则在求mid时val故意很大时会让mid越界
        if(left>right  || val<arr[0] || val>arr[arr.length-1] ){
            return -1;
        }
        //求出mid，数组元素都一样怎么破，就会报零错误。就是找findvalue在区间中的占比
        int mid=left+(right-left)*(val-arr[left])/(arr[right]-arr[left]);
        int midval=arr[mid];
        if(val>midval){
            return insertsearch(arr,mid+1,right,val);
        }
        else if(val<midval){
            return insertsearch(arr,left,mid-1,val);
        }
        else return mid;
    }

}
