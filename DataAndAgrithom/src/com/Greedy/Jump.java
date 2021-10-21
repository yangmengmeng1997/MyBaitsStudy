package com.Greedy;

public class Jump {
    static int result=1;  //初始值应该为1
    public static void main(String[] args){
        int[] nums={2,3,1,1,4};
        System.out.println(jump(nums));
    }
    public static int jump(int[] nums) {
        int nextdistance=0;  //下次更新的最大下标
        int curdistance=0;   //当前所能到达的最远下标
        int ans=0;
        for(int i=0;i<nums.length-1;i++){  //只需到下标为nums[length-2]的位置就行，因为最后一个总能跳过
            nextdistance=Math.max(nums[i]+i,nextdistance); //找到最大的覆盖区间
            if(i==curdistance){  //只要越过当前的最大距离立刻更新最大的距离
                ans++;
                curdistance=nextdistance;
            }
        }
        return ans;
    }

}
