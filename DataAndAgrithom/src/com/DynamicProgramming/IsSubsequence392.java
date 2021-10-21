package com.DynamicProgramming;

public class IsSubsequence392 {
    public static void main(String[] args) {
        String s = "axc";
        String t = "ahbgdc";
        System.out.println(isSubsequence(s,t));
    }
    //动态规划
    public static boolean isSubsequence(String s, String t) {
        int[][] dp=new int[s.length()+1][t.length()+1];
        for(int i=0;i<s.length();i++)
            for(int j=0;j<t.length();j++){
                if(s.charAt(i)==t.charAt(j)){
                    dp[i+1][j+1]=dp[i][j]+1;
                }else{
                    dp[i+1][j+1]=Math.max(dp[i][j+1],dp[i+1][j]);
                }
            }
        return dp[s.length()][t.length()]==s.length();
    }
    //暴力

}
