package com.WeeklyContest;

import java.util.ArrayList;
import java.util.List;

public class MaxCompatibilitySum5825 {
    static List<int[]> path=new ArrayList<>();
    static int sum=0;
    public static void main(String[] args) {
        int[][] students = {{0,0},{0,0},{0,0}};
        int[][] mentors = {{1,1},{1,1},{1,1}};
        System.out.println(maxCompatibilitySum(students,mentors));
    }
    public  static int maxCompatibilitySum(int[][] students, int[][] mentors) {
        boolean[] used=new boolean[students.length];
        dfs(students,mentors,used);
        return sum;
    }
    //全排列数组
    public static void dfs(int[][] num,int[][] mentors,boolean[] used){
        if(path.size()==num.length){
            int temp=Caluate(path,mentors);
            if(sum<temp)
                sum=temp;
        }
        for(int i=0;i<num.length;i++){
            if(!used[i]){
                path.add(num[i]);
                used[i]=true;
                dfs(num,mentors,used);
                path.remove(path.size()-1);
                used[i]=false;
            }
        }
    }
    public static int Caluate(List<int[]> students, int[][] mentors){
        int sum=0;
        int m=mentors.length;
        int n=mentors[0].length;
        for(int i=0;i<m;i++){
            int[] temp=students.get(i);
            for(int j=0;j<n;j++){
                sum+=temp[j] ^ mentors[i][j];   //异或
            }
        }
        return m*n-sum;   //相减就是反面，就是同或
    }
    //辅助打印函数
    public static void Print(List<int[]> res){
        for(int i=0;i<res.size();i++){
            int[] temp=res.get(i);
            for(int j=0;j<temp.length;j++)
                System.out.print(temp[j]+" ");
            System.out.println();
        }
    }
}
