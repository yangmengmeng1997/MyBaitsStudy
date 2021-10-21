package com.Greedy;

public class MaxProfit {
    public static void main(String[] args){
        int[] prices={1,2,3,4,5,6};
        System.out.println(maxProfit(prices));
    }
    public static int maxProfit(int[] prices) {
        int totalprofit=0;
        for(int i=1;i<prices.length;i++){
            int profit=prices[i]-prices[i-1];
            if(profit>0){
                totalprofit+=profit;
            }
        }
        return totalprofit;
    }
}
