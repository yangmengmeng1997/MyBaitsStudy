package com.DFSExercise;

//二分法递归建树，挺好
public class SortedArrayToBST {
    public static  void main(String[] args){
        int []nums={-10,-3,0,5,9};
        TreeNode root=sortedArrayToBST(nums);
        System.out.println(height(root));
    }
    public static TreeNode sortedArrayToBST(int[] nums) {
        return process(nums,0,nums.length-1);
    }
    public static TreeNode process(int[] nums,int left,int right) {
        TreeNode node=null;
        if(left<=right){
            int mid=left+(right-left)/2;
            node=new TreeNode(nums[mid]);
            node.left=process(nums,left,mid-1);
            node.right=process(nums,mid+1,right);
        }
        return node;
    }
    public static int height(TreeNode root){
        if(root==null){
            return 0;
        }
        return Math.max(height(root.left),height(root.right))+1;
    }
}
