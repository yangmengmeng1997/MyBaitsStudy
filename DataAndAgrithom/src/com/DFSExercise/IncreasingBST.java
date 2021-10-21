package com.DFSExercise;

import java.util.ArrayList;
import java.util.List;

public class IncreasingBST {
    public static void main(String[] args){
        Integer[] arr={5,3,6,2,4,null,8,1,null,null,null,null,null,7,9};
        TreeNode root=CreateTree(arr,0);
        List<Integer> list=new ArrayList<>();
        Inorder(root,list);
        TreeNode newroot=increasingBST(list,0);
        System.out.println("ok");
    }
    public static TreeNode increasingBST(List<Integer> res,int index) {
        TreeNode node=null;
        if(index<res.size()){
            node=new TreeNode(res.get(index));
            node.left=null;
            node.right=increasingBST(res,index+1);
        }
        return node;
    }
    public static void Inorder(TreeNode root,List<Integer> res){
        if(root==null) return;
        if(root.left!=null) Inorder(root.left,res);
        res.add(root.val);
        if(root.right!=null) Inorder(root.right,res);
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
