package com.DFSExercise;


//783. 二叉搜索树节点最小距离,注意题目限制了二叉搜索树，所以与root节点最近的两个节点
//1,找到左子树最右边的节点，找到右子树最左边的节点，和根节点比较即可
//之前使用二叉搜索树来做的，但是不会递归每个节点
public class MinDiffInBST {
    static int min=9999;
    static int count=0;
    static int[] Inorder=new int[100];
    public static void main(String[] args){
        Integer[] arr={1,0,48,null,null,12,49};
        TreeNode root=CreateTree(arr,0);
        InorderPre(Inorder,root);   //test is ok
        for(int i=0;i<count-1;i++){
            if(min>(Inorder[i+1]-Inorder[i])){
                min=Inorder[i+1]-Inorder[i];
            }
        }
        System.out.println(min);
    }
    //中序遍历顺序存数组中，再进行遍历
    public static void InorderPre(int[] inorder,TreeNode root){
        if(root==null) return;
        if(root.left!=null) InorderPre(inorder,root.left);
        inorder[count++]=root.val;
        if(root.right!=null) InorderPre(inorder,root.right);
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

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                '}';
    }
}

