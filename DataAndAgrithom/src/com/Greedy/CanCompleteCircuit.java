package com.Greedy;

public class CanCompleteCircuit {
    public static void main(String[] args) {
        int[] gas  = {1,2,3,4,5};
        int[] cost = {3,4,5,1,2};
        System.out.println(canCompleteCircuit(gas,cost));
    }
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        for (int i=0;i<gas.length;i++){  //遍历数组每一个可能的起始位置
            int j=i;
            int res=0;
            //循环看能不能走完
            while ((gas[(j+1)%gas.length]+res)>=cost[j%cost.length]){
                res=gas[(j+1)%gas.length]-cost[j%cost.length];
                j=(j+1)%gas.length;
                if(j==i) return i;  //如果可以回到起点就返回原始起点
            }
        }
        return -1;
    }
}
