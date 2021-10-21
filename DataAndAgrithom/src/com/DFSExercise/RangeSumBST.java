package com.DFSExercise;

public class RangeSumBST {
    static int sum=0;
    public static void main(String []args){
        Integer[] arr={10,5,15,3,7,null,18};
        TreeNode root=CreateTree(arr,0);
        rangeSumBST(root,7,15);
        System.out.println(sum);
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
    public static void rangeSumBST(TreeNode root, int low, int high) {
        if(root==null) return ;
        if(root.left!=null) rangeSumBST(root.left,low,high);
        if(root.val>=low && root.val<=high){
            sum+=root.val;
        }
        if(root.right!=null) rangeSumBST(root.right,low,high);
    }
}
