package com.DFSExercise;

public class IsBalanced {
    public static void main(String[] args){
        Integer[] arr={1,2,2,3,3,null,null,4,4};
        TreeNode root=CreateTree(arr,0);
        System.out.println(isBalanced(root));
    }
    public static boolean isBalanced(TreeNode root){
        if(root==null) return true;
        return isBalanced(root.left)&& isBalanced(root.right) && Math.abs(height(root.left)-height(root.right))<=1;
    }
    public static int height(TreeNode root){
        if(root==null) return 0;
        return Math.max(height(root.left),height(root.right))+1;
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
