package com.Tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Tree1 {
    public static void main(String[] args){
        Node root=new Node(2);
        Node node2=new Node(1);
        Node node3=new Node(3);
        Node node4=new Node(4);
        Node node5=new Node(5);
        Node node6=new Node(6);
        Node node7=new Node(7);
//        Node node8=new Node(8);
        root.left=node2;
        root.right=node3;
        node2.left=node4;
        node2.right=node5;
        node3.left=node7;

        //Node.OrderUnRecur(root);
        //Node.postOrderUnRecur(root);
        //Node.OrderUnRecur(root);
        System.out.println(Node.isCBT(root));
    }

}
//叶子节点
class Node{
    int val;
    Node left;
    Node right;
    int depth;  //添加新的节点，加入深度信息
    public Node(int val) {
        this.val = val;
    }
    @Override
    public String toString() {
        return "Node{" +
                "val=" + val +
                '}';
    }
    public static void preTravel(Node root){
        if(root==null){
            return;
        }
        System.out.println(root);
        preTravel(root.left);
        preTravel(root.right);
    }
    public static void OrderTravel(Node root){
        if(root==null){
            return;
        }
        OrderTravel(root.left);
        System.out.println(root);
        OrderTravel(root.right);
    }
    public static void PostTravel(Node root){
        if(root==null){
            return;
        }
        PostTravel(root.left);
        PostTravel(root.right);
        System.out.println(root);
    }
    //非递归先序遍历,ok
    public static void preOrderUnRecur(Node root){
        Stack<Node> stack=new Stack<>();
        //根节点压栈
        stack.push(root);
        //栈非空就出栈栈顶元素
        while (!stack.empty()){
            Node temp=stack.pop();
            System.out.println(temp);  //先输出根节点
            if(temp.right!=null){   //栈是先进后出，所以先入右孩子节点
                stack.push(temp.right);
            }if(temp.left!=null){
                stack.push(temp.left);
            }
        }
    }
    //非递归中序遍历,将树的左子树入栈，出栈时输出节点，再对右子树入栈操作
    //测试通过，不对，这个树过于规则，测试可能不对
    public static void OrderUnRecur(Node root){
       Stack<Node> stack=new Stack<>();
        //根节点压栈
        stack.push(root);
        while (root.left!=null){
            stack.push(root.left);
            root=root.left;
        }//将根节点和其整个左子树入栈
        //栈非空就出栈栈顶元素
        while (!stack.empty()){
            Node temp=stack.pop();  //获取当前节点并且输出
            System.out.println(temp);
            if(temp.right!=null){  //处理右节点
                Node node=temp.right;
                stack.push(node);
                while (node.left!=null){  //右节点有左子树
                    stack.push(node.left);
                    node=node.left;
                }
            }
        }
    }
    //后序遍历，先头右左-->然后压入辅助栈中，倒序就是后序遍历
    public static void postOrderUnRecur(Node root){
        Stack<Node> stack=new Stack<>();
        Stack<Node> tempstack=new Stack<>();
        //根节点压栈
        stack.push(root);
        //栈非空就出栈栈顶元素
        while (!stack.empty()){
            Node temp=stack.pop();
            tempstack.push(temp); //将当前访问节点也如新栈，倒序输出
            if(temp.left!=null){    //先压左，再压右 就是栈中就是头左右--倒序之后就是右左头是后序顺序
                stack.push(temp.left);
            }
            if(temp.right!=null){
                stack.push(temp.right);
            }
        }
        //倒叙输出栈的元素，即为后序顺序
        while (!tempstack.empty()){
            System.out.println(tempstack.pop());
        }
    }
    //树的宽度优先遍历,用队列即可,计算树的最大深度
    public static int BroadTravel(Node root){
        int max=0;
        if(root==null)
            return max;
        Queue<Node> queue=new LinkedList<>();
        queue.add(root);
        root.depth=1;
        while (!queue.isEmpty()) {
            Node temp = queue.remove();
            System.out.println(temp);
            if(max<temp.depth)
                max=temp.depth;
            if (temp.left != null) {
                queue.add(temp.left);
                temp.left.depth = temp.depth + 1;
            }
            if (temp.right != null) {
                queue.add(temp.right);
                temp.right.depth=temp.depth+1;
            }
        }
        return max;
    }
    //计算树的层数最多的节点
    //记录每个节点对应所在的层数即可
    //不用改变树的数据结构来而进行的统计
    public static int MaxNodes(Node root){
        int max=0;
        //用hashmap存储比较好
        HashMap<Node,Integer> hashMap=new HashMap<>();
        int currentlevel;   //当前层深度，指示器作用
        int currentNodes;   //当前层节点个数
        if(root==null)
            return max;
        //根节点不空，记录相关数据
        currentlevel=1;
        currentNodes=0;
        hashMap.put(root,currentlevel);
        Queue<Node> queue=new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node temp = queue.remove();
            //每次访问一个节点，得到一个节点的层数并且计算出对应的node数量
            int curNodeLevel=hashMap.get(temp);
            if(curNodeLevel==currentlevel){  //如果当前层的节点就是当前节点所在的层
                currentNodes++;   //节点数++
            }else{
                max=Math.max(max,currentNodes);  //取最大为当前节点，节点已经去向下一层
                currentlevel++;  //去向下一层
                currentNodes=1;  //重置下一层节点的个数
            }
            System.out.println(temp);
            if (temp.left != null) {
                hashMap.put(temp.left,curNodeLevel+1);  //孩子节点是当前节点深度+1
                queue.add(temp.left);
            }
            if (temp.right != null) {
                hashMap.put(temp.right,curNodeLevel+1);
                queue.add(temp.right);
            }
        }
        return max;
    }

    public static int prevalue=Integer.MIN_VALUE;  //全局变量
    //二叉排序树,利用了中序遍历必然有序的思想，在中序遍历处用了判断条件
    public static boolean isBST(Node root){
        //判断当前节点
        if(root==null)
            return true;
        //检查左子树
        boolean isleft=isBST(root.left);
        if(!isleft) return false; //左子树不是二叉排序树直接返回false
        //处理当前节点
        if(root.val<=prevalue){
           return false;
        }else{
            prevalue=root.val;
        }
        return isBST(root.right);
    }
    //判断一棵二叉树是否是完全二叉树
    //层次遍历判断每一个节点
    public static boolean isCBT(Node root){
        boolean isLeaf=true;
        Queue<Node> queue=new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Node node=queue.remove();
            //有双孩子节点不用判断
            if(node.left!=null && node.right!=null){
                queue.add(node.left);
                queue.add(node.right);
            }
            //左空右孩子必定不是完全二叉树
            else if(node.left==null && node.right!=null)
                return false;
            //遇到单个孩子节点，左边不为空右边为空，要后续判断
            else if(node.left!=null && node.right==null){
                queue.add(node.left);
                //加下来要判断节点都为叶子结点即可
                while (!queue.isEmpty()){
                    Node temp=queue.remove();
                    if(temp.left==null && temp.right==null) continue;
                    else{
                        isLeaf=false;
                        break;
                    }
                }
                return isLeaf;
            }
        }
        return true;
    }
    //判断一棵树是否是满二叉树

    //判断一棵树是否为平衡二叉树
    //左子树为平衡二叉树，右子树为平衡二叉树，左右子树的差值为1
    //构造返回信息,需要子树是否平衡以及自己的高度
    public static boolean isBalanced(Node root){
        return process(root).isBalance;
    }
    public static class ReturnType{
        public boolean isBalance;
        public int height;
        public ReturnType(boolean isBalance, int height) {
            this.isBalance = isBalance;
            this.height = height;
        }
    }
    public static ReturnType process(Node root){
        if(root==null){
            //树空返回的信息，也就是出口
            return new ReturnType(true,0);
        }
        ReturnType leftData=process(root.left);  //左子树返回信息
        ReturnType rightData=process(root.right); //右子树返回信息
        //处理当前节点需要返回的两个信息
        int height=Math.max(leftData.height,rightData.height)+1;
        boolean isBalance=(leftData.isBalance && rightData.isBalance && Math.abs(leftData.height-rightData.height)<=1);
        return new ReturnType(isBalance,height);   //当前节点需要返回的两个参数信息
    }

}
