package com.Exercise;
import java.util.Scanner;
public class Exercise2 {
    static boolean flag=false;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] nums={1,1,1,1};   //只存储a,b,c,d这四个数
        int x;
        int N;
        int num=0;   //控制行数
        while(in.hasNextLine()){
            num++;
            String s=in.nextLine();
            //System.out.println(s);
            String[] strings = s.trim().split(" ");
            x=Integer.parseInt(strings[0]);
            N= Integer.parseInt(strings[1]);
            //System.out.println(x+N);
            System.out.printf("Case %d:\n",num);
            dfs(nums,0,N,x,num);
            if(!flag){
                System.out.println("No such numbers.");
            }
            flag=false;   //循环一轮复位
        }
    }
    public static void dfs(int[] nums,int depth,int N,int x,int num){
        if(depth==4 && check(nums)){
            if(IsOk(nums,x)){
                flag=true;
                System.out.printf("%d^%d+%d^%d+%d^%d=%d^%d\n",nums[0],x,nums[1],x,nums[2],x,nums[3],x);
            }
            return;
        }
        for(int i=1;i<=N && depth<4;i++){
            //剪枝
            nums[depth] = i;
            dfs(nums, depth + 1, N,x,num);
        }
    }
    //剪枝输出排列次序
    public static boolean check(int[] nums){
        for(int i=1;i<nums.length;i++){
            if(nums[i-1]>nums[i]) return false;
        }
        return true;
    }
    public static boolean IsOk(int[] nums,int x){
        if(nums[0]==1 && nums[1]==1 &&nums[2]==1 &&nums[3]==1) return false;
        return pow(nums[0],x)+pow(nums[1],x)+pow(nums[2],x)==pow(nums[3],x);
    }
    public static int pow(int a,int x){
        int sum=1;
        for(int i=1;i<=x;i++)
            sum*=a;
        return sum;
    }

}
