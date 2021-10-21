package com.DFSExercise;

import java.util.ArrayList;
import java.util.List;

public class LeafSimilar {
     public static void main(String[] args){
         List<Integer> res1=new ArrayList<>();
         List<Integer> res2=new ArrayList<>();
         Integer []arr1={3,5,1,6,7,4,2,null,null,null,null,null,null,9,11,null,null,8,10};
         Integer []arr2={3,5,1,6,2,9,8,null,null,7,4};
         TreeNode root1=CreateTree(arr1,0);
         TreeNode root2=CreateTree(arr2,0);
         process(root1,res1);
         process(root2,res2);
         System.out.println(res1.equals(res2));
     }
     //求叶子序列
    public static void process(TreeNode root,List<Integer> list){
         if(root==null) return ;
         if(root.left==null && root.right==null) list.add(root.val);
         process(root.left,list);
         process(root.right,list);
    }


    //构造辅助函数
    public static TreeNode CreateTree(Integer[] arr, int index){
        TreeNode treeNode=null;
        if(index<arr.length && arr[index]!=null){  //是0的话不用再递归,相当于子树是空树
            treeNode=new TreeNode(arr[index]);
            treeNode.left=CreateTree(arr,2*index+1); //创建左子树
            treeNode.right=CreateTree(arr,2*index+2);
        }
        return treeNode;
    }

}
