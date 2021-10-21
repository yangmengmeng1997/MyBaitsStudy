package com.Tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//给定一个二叉树，检查它是否是镜像对称的
public class Tree3 {
    static int count=0;
    public static boolean flag=false;
    static int res=0;
    public static void main(String[] args){
        Integer []arr1={21,7,14,1,1,2,2,3,3};
        TreeNode root1=CreateTree(arr1,0);  //test ok
        //TreeNode root2=CreateTree(arr,0);  //test ok
        //preTravel(root1);    //test ok
        findTiltProcess(root1);
        //preTravel(root1);
        System.out.println(sumnode(root1));
        List<Employee> list =new LinkedList<>();
        List<Integer> su1=new LinkedList<>();
        List<Integer> su2=new LinkedList<>();
        List<Integer> su3=new LinkedList<>();
        su1.add(2);
        su1.add(3);
        Employee e1=new Employee(1,5,su1);
        Employee e2=new Employee(2,3,su2);
        Employee e3=new Employee(3,3,su3);
        list.add(e1);
        list.add(e2);
        list.add(e3);

        getImportance(list,1,res);
        System.out.println(res);

    }
    public static boolean isSymmetric(TreeNode left,TreeNode right){
        if(left==null && right==null) return true;
        if(left==null || right==null || left.val!=right.val) return false;
        return isSymmetric(left.left,right.right) && isSymmetric(left.right,right.left);
    }

    public static void preTravel(TreeNode root){
        if(root==null) return;
        System.out.println(root.val);
        preTravel(root.left);
        preTravel(root.right);
    }

    //构造辅助函数
    public static TreeNode CreateTree(int[] arr,int index){
        TreeNode treeNode=null;
        if(index<arr.length && arr[index]!=0){  //是0的话不用再递归,相当于子树是空树
            treeNode=new TreeNode(arr[index]);
            treeNode.left=CreateTree(arr,2*index+1); //创建左子树
            treeNode.right=CreateTree(arr,2*index+2);
        }
        return treeNode;
    }
    //构造辅助函数
    public static TreeNode CreateTree(Integer[] arr,int index){
        TreeNode treeNode=null;
        if(index<arr.length && arr[index]!=null){  //是0的话不用再递归,相当于子树是空树
            treeNode=new TreeNode(arr[index]);
            treeNode.left=CreateTree(arr,2*index+1); //创建左子树
            treeNode.right=CreateTree(arr,2*index+2);
        }
        return treeNode;
    }

    public static void swap(TreeNode root){
        TreeNode temp=root.left;
        root.left=root.right;
        root.right=temp;
    }
    public static TreeNode mirrorTree(TreeNode root){
        if(root==null) return null;   //出口
        swap(root);  //为什么传root的两个左右孩子交换没有效果？
        mirrorTree(root.left);
        mirrorTree(root.right);
        return root;
    }

    //二叉搜索树的最近祖先节点
    public static List<TreeNode> findParent(TreeNode node,TreeNode target,List<TreeNode> res){
         if(node==null) return null;
         if(node.val<target.val){
             res.add(node);
             findParent(node.right,target,res);
         }else if(node.val>target.val){
             res.add(node);
             findParent(node.left,target,res);
         }else{
             res.add(node);  //把自己节点添加进去，防止出现父节点是自己的情况
             return res;   //找到list
         }
         return res;
    }

    //二叉树的最近祖先，层次遍历
    public static List<TreeNode> findParent2(TreeNode root,TreeNode target,List<TreeNode> res){
        HashMap<TreeNode,TreeNode> map=new HashMap<>();
        Queue<TreeNode> queue=new LinkedList<>();
        //层次遍历压缩节点
        map.put(root,root);  //根节点的父节点是自己
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode temp=queue.remove();
            if(temp.val==target.val){  //将祖先节点取出,包括自己和root节点
                res.add(temp);
                TreeNode parent=map.get(temp);
                while(parent!=root){
                    res.add(parent);
                    parent=map.get(parent);
                }
                res.add(root);
            }
            if(temp.left!=null){
                queue.add(temp.left);
                map.put(temp.left,temp);
            }
            if(temp.right!=null){
                queue.add(temp.right);
                map.put(temp.right,temp);
            }
        }
        return res;
    }

    //判断两颗树的结构是不是一样的,以子树B为基准来判断
    public static boolean isSubStructureProcess(TreeNode A,TreeNode B) {
        return (A!=null || B!=null)&& recur(A,B) &&isSubStructureProcess(A.left,B.left) && isSubStructureProcess(A.right,B.right);
    }
    //判断当前A树是否包含B树
    public static boolean recur(TreeNode A,TreeNode B){
        if(B==null) return true;
        if(A==null || A.val!=B.val) return false;
        return recur(A.left,B.left) && recur(A.right,B.right);
    }

    //100. 相同的树
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null && q==null) return true;
        if(p!=null && q!=null){
          return p.val==q.val && isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
        }
        return false;
    }
    //最大深度
    public int maxDepth(TreeNode root) {
        if(root==null) return 0;
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }
    //bfs求最大深度
    public static int maxDepthBfs(TreeNode root) {
         Queue<TreeNode> queue=new LinkedList<>();
         int level=0;
         queue.add(root);
         while(!queue.isEmpty()){
             int size=queue.size();  //提前求出当前层的所有size
             while(size>0){
                 TreeNode temp=queue.remove();
                 if(temp.left!=null)
                     queue.add(temp.left);
                 if(temp.right!=null)
                     queue.add(temp.right);
                 size--;   //手动将一层的size相减，只靠queue.size来判断是不行的，因为队列中会有不同层的节点不断入队
             }  //将一层的节点处理完毕,如此就完成了层次遍历
             level++;
         }
         return level;
    }

    //112.路劲总和
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null) return false;
        if(root.left==null && root.right==null) {
            return root.val==targetSum;
        }
        if(root.left!=null) return hasPathSum(root.left,targetSum-root.left.val);
        if(root.right!=null) return hasPathSum(root.right,targetSum-root.right.val);
        return hasPathSum(root.left,targetSum-root.left.val)|| hasPathSum(root.right,targetSum-root.right.val);
    }

    //257. 二叉树的所有路径
    //逻辑就是有点问题，可以修改,StringBuffer???
    //dfs就是包含了回溯，再加额外的判断就是会出错
    public List<String> binaryTreePaths(TreeNode root) {
        return null;
    }
    public static List<String> binaryTreePaths(TreeNode root,StringBuffer str,List<String> res) {
        if(root==null){
            return null;
        }
        str.append(root.val);
        if(root.left==null && root.right==null) {
            res.add(str.toString());
            return res;
        }
        binaryTreePaths(root.left,new StringBuffer(str).append("->"),res);
        binaryTreePaths(root.right,new StringBuffer(str).append("->"),res);
        return res;
    }
    //563. 二叉树的坡度
    //1.先将树的节点进行求和 //testok
    public static int sumnode(TreeNode root){
        if(root==null) return 0;
        return sumnode(root.left)+sumnode(root.right)+root.val;
    }
    //2.将原来树的节点构造为梯度树
    public static void findTiltProcess(TreeNode root){
        if(root==null) return ;
        root.val=Math.abs(sumnode(root.left)-sumnode(root.right));
        findTiltProcess(root.left);
        findTiltProcess(root.right);
    }
    //690. 员工的重要性
    //1.根据传入的id找对应的员工
    public static Employee findEmployeeById(List<Employee> employees,int id){
        for(int i=0;i<employees.size();i++){
            if(id==employees.get(i).id)
                return employees.get(i);
        }
        return null;
    }
    //2.开始编写
    public static void getImportance(List<Employee> employees, int id,int res) {
        Employee employee=findEmployeeById(employees,id);  //找到初始id
        res+=employee.importance;
        for(int i=0;i<employee.subordinates.size();i++){
            int tempid=employee.subordinates.get(i);
            getImportance(employees,tempid,res);
        }
    }

}
class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;

    public Employee(int id, int importance, List<Integer> subordinates) {
        this.id = id;
        this.importance = importance;
        this.subordinates = subordinates;
    }
};

