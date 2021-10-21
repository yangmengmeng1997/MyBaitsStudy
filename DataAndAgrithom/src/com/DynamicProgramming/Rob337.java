package com.DynamicProgramming;

public class Rob337 {
    //树形DP入门
    public static void main(String[] args) {
        Integer[] nums={3,2,3,null,3,null,1};
        TreeNode node=CreateTree(nums,0);
        //PreTravel(node);
        System.out.println(rob1(node));
        //System.out.println(max);
    }
    public static int rob1(TreeNode root){
        int[] result = robInternal(root);
        return Math.max(result[0], result[1]);
    }
    public static int[] robInternal(TreeNode root) {
        if (root == null) return new int[2];
        int[] result = new int[2];   //0 表示不选，1表示选

        int[] left = robInternal(root.left);   //递归左子树
        int[] right = robInternal(root.right); //递归右子树

        //处理当前节点，0表示不选当前节点，那么就可以选择左子树和右子树
        //而左子树中又面面临选与不选的情况。同样右子树同理可得。
        result[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        //这里表示选择了当前节点，那么就表示不选左孩子和不选右孩子的最大值+当前节点的值
        result[1] = left[0] + right[0] + root.val;

        return result;
    }
    public static int rob(TreeNode root) {
        if(root==null) return 0;
        if(root.left==null && root.right==null) return root.val;
        int val=root.val;   //偷父节点
        if(root.left!=null) val+=rob(root.left.left)+rob(root.left.right);  //偷了父节点就略过左孩子
        if(root.right!=null) val+=rob(root.right.left)+rob(root.right.right);//偷了父节点就略过右孩子
        //不偷父节点,偷左右子树节点
        int val2=rob(root.left)+rob(root.right);
        return Math.max(val,val2);
    }


    //数组构造一个二叉树
    public static TreeNode CreateTree(Integer[] arr, int index){
        TreeNode treeNode=null;
        if(index<arr.length && arr[index]!=null){  //是0的话不用再递归,相当于子树是空树
            treeNode=new TreeNode(arr[index]);
            treeNode.left=CreateTree(arr,2*index+1); //创建左子树
            treeNode.right=CreateTree(arr,2*index+2);
        }
        return treeNode;
    }
    //测试建造树的结构是否正确
    public static void PreTravel(TreeNode root){
        if(root==null) return ;
        System.out.println(root.val);
        if(root.left!=null) {
            PreTravel(root.left);
        }
        if(root.right!=null)
            PreTravel(root.right);
    }

}
class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
