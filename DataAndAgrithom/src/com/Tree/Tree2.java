package com.Tree;

import java.util.*;

//剑指 Offer 32 - II. 从上到下打印二叉树 II
public class Tree2 {
    static int count=0;
    static int ans=0;
    public static void main(String[] args){
        int []arr={3,1,4,0,2};
        TreeNode node=CreateTree(arr,0);
        List<Integer> list=InOrder(node,new ArrayList<>());
    }
    //二叉树的层次遍历
    //这bfs不对，应该用for循环
    public static List<List<Integer>> levelOrder(TreeNode root) {
        if(root==null)
            return new ArrayList<>();
        int curlevel=1;
        List<List<Integer>> finalList=new ArrayList<>();
        List<Integer> temp=new ArrayList<>();
        Queue<TreeNode> queue=new LinkedList();
        HashMap<TreeNode,Integer> map=new HashMap<>();
        map.put(root,curlevel);
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode node= queue.remove();
            int curNodelevle=map.get(node);  //取当前节点层数
            if(curNodelevle==curlevel){
                temp.add(node.val);  //同一层的加入链表，最后一个节点无法判断
            }else{  //不相等就进入下一层
                curlevel++;
                finalList.add(temp); //合并结果,存储的是一些引用数据，清空引用数据，自己也会清空
                temp=new ArrayList<>();//创建新的存储区域，这样原来的应用数据就不会清空
                temp.add(node.val);  //temp加入这一层的第一个数据
            }
            if(node.left!=null){
                queue.add(node.left);
                map.put(node.left,curlevel+1);
            }
            if(node.right!=null){
                queue.add(node.right);
                map.put(node.right,curlevel+1);
            }
        }
        finalList.add(temp);   //处理最后一层的节点list要加上去
        return finalList;
    }
    //bfs
    public static List<List<Integer>> levelOrder1(TreeNode root){
        Queue<TreeNode> queue=new LinkedList<>();
        List<List<Integer>> res=new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            //每一层都是新的temp
            List<Integer> temp=new ArrayList<>();
            //遍历这一层的节点并且将子节点入栈
            //用queue.size做初始化条件非常好，但是把他设置为循环次数就无法控制了
            for(int i=queue.size();i>0;i--){
                TreeNode node=queue.poll();
                temp.add(node.val);
                if(node.left!=null) queue.add(node.left);
                if(node.right!=null) queue.add(node.right);
            } //一层遍历结束
            res.add(temp);
        }
        return res;
    }

    public static class ReturnType{
        int height;
        boolean isBST;

        public ReturnType(int height, boolean isBST) {
            this.height = height;
            this.isBST = isBST;
        }
    }
    public static ReturnType process(TreeNode root){
        if(root==null){
            return new ReturnType(0,true);
        }
        ReturnType leftdata=process(root.left);
        ReturnType rightdata=process(root.right);
        int height=Math.max(leftdata.height,rightdata.height)+1;
        boolean isBST= (Math.abs(leftdata.height-rightdata.height)<=1 && leftdata.isBST && rightdata.isBST);
        return new ReturnType(height,isBST);
    }
    public static boolean isBalanced(TreeNode root){
        return process(root).isBST;
    }

    //求最大深度 dfs
    public static int maxDepth(TreeNode root) {
        if(root==null)
            return 0;
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }
    //求最大深度 bfs
    public static int maxDepthbfs(TreeNode root) {
        int level=0;
        Queue<TreeNode> queue=new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            for(int i=queue.size();i>0;i--){
                TreeNode temp=queue.poll();
                if(temp.left!=null) queue.add(temp.left);
                if(temp.right!=null) queue.add(temp.right);
            }
            level++;
        }
        return level;
    }

    //辅助函数
    public static void PreOrder(TreeNode node){
        if(node!=null)
            System.out.println(node.val);
        if(node.left!=null)
            PreOrder(node.left);
        if(node.right!=null)
            PreOrder(node.right);
    }
    //递归构造二叉树，辅助函数
    public static TreeNode CreateTree(int[] arr,int index){
        TreeNode treeNode=null;
        if(index<arr.length && arr[index]!=0){  //是0的话不用再递归,相当于子树是空树
            treeNode=new TreeNode(arr[index]);
            treeNode.left=CreateTree(arr,2*index+1); //创建左子树
            treeNode.right=CreateTree(arr,2*index+2);
        }
        return treeNode;
    }

    //第k大节点
    public static int kthLargest(TreeNode root, int k) {
        List<Integer> res=InOrder(root,new ArrayList<>());
        return res.get(res.size()-k);
    }
    //其实不需要存储一轮节点啊，只需要保存到K个节点就行了
    public static List<Integer> InOrder(TreeNode root, List<Integer> res){
        //搜索二叉树的中序遍历就是递增有序，那么倒过来就是逆序了，即右根左
        if(root==null) return res;
        if(root.left!=null){
            InOrder(root.left,res);
        }
        res.add(root.val);  //中序存储信息
        if(root.right!=null)
            InOrder(root.right,res);
        return res;
    }
    //中序反转
    public static void InOrderRevese(TreeNode root, int k){
        //搜索二叉树的中序遍历就是递增有序，那么倒过来就是逆序了，即右根左
        if(root.right!=null)  InOrderRevese(root.right,k);
        //处理当前节点
        if(++count==k){
            ans=root.val;
            return ;  //自己有一个返回值返回
        }
        if(root.left!=null)  InOrderRevese(root.left,k);
    }
    //第k大节点
    public static int kthLargest1(TreeNode root, int k) {
        return ans;
    }


}
class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
}
