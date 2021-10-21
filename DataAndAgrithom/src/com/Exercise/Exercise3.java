package com.Exercise;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Exercise3 {
    static List<String> path=new LinkedList<>();
    static List<List<String>> res=new LinkedList<>();
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int num=0;
        while(sc.hasNextLine()){
            num++;
            String[] nums={"0","1","2","3","4","5","6","7","8","9"};
            StringBuffer s=new StringBuffer("");
            int N;
            N=sc.nextInt();
            boolean[] used=new boolean[10];
            System.out.printf("Case %d:\n",num);
            dfs(nums,N,used);  //字母要不一样才行
            if(res.size()==0){
                System.out.println("    No such numbers");
            }
            res.clear();  //复位
            path.clear();//复位
        }
    }
    public static void dfs(String[] nums,int N,boolean[] used){
        //剪枝
        if(path.size()==6 && path.get(0).compareTo(path.get(5))<0) return;
        if(nums.length==path.size()){
            StringBuffer sb1=new StringBuffer();
            StringBuffer sb2=new StringBuffer();
            for(int i=0;i<5;i++){
                sb1.append(path.get(i));
            }
            for(int i=5;i<10;i++){
                sb2.append(path.get(i));
            }
            int num1=Integer.parseInt(sb1.toString());
            int num2=Integer.parseInt(sb2.toString());
            if( num1%num2==0 && num1/num2==N){
                System.out.printf("    %s/%s=%d\n",sb1,sb2,N);
                res.add(new ArrayList<>(path));
            }
            return;
        }
        for(int i=nums.length-1;i>=0;i--){
            if(used[i]==true) continue;
            path.add(nums[i]);
            used[i]=true;
            dfs(nums,N,used);
            path.remove(new String(nums[i])); //回溯
            used[i]=false;
        }
    }


}
