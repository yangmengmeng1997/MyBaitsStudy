package com.WeeklyContest;

/*
  对于每个 0 <= i < k ，先标记出位于 s[removable[i]] 的字符，
  接着移除所有标记过的字符，然后检查 p 是否仍然是 s 的一个子序列。
  先标记所有的k，再移除所有的元素而不是顺序标记元素的同时逐个删除元素
  二分算法来求
 */
public class MaximumRemovals {
    static boolean[] flag=new boolean[100000];
    public static void main(String[] args) {
        String a="abcbddddd";
        String b="abcd";
        int[] removable = {3,2,1,4,5,6};
        System.out.println(maximumRemovals1(a,b,removable));
    }
    public static int maximumRemovals(String s, String p, int[] removable) {
        int ans=0;
        int left=0;
        int right=removable.length;
        while(left<=right){
            int mid=(left+right)/2;
            if(isSubsequence(s,p,mid,removable)){   //成功扩大搜索范围,
                ans=Math.max(ans,mid);  //可选答案
                left=mid+1;
            }else{
                right=mid-1;
            }

        }
        return ans;   //???
    }
    //判断子序列完成
    public static boolean issubstr(String a,String b,int k,int[] removable){
        for(int i=0;i<a.length();i++) flag[i]=false;
        for(int i=0;i<=k;i++) flag[removable[i]]=true;
        int i=0;
        int j=0;
        for(;i<a.length() && j<b.length();i++){
            if(flag[i]==true) continue;
            if(a.charAt(i)==b.charAt(j)){
                j++;
            }
        }
        if(j==b.length()) return true;
        else return false;
    }

    public static int maximumRemovals1(String s, String p, int[] removable) {
        int l = 0;
        int r = removable.length-1;
        while(l <= r){
            int mid = l + (r-l)/2;
            if(!isSubsequence(s, p,mid,removable))
                r = mid-1;
            else
                l = mid+1;
        }
        return r+1;   //r+1？最大区间r+1
    }
    //判断子序列
    public static boolean isSubsequence(String s, String p, int k,int removable[]) {// 判断p是否是s的子串,且不能包含被删除的元素
        int m = s.length();
        int n = p.length();
        int i = 0;
        int j = 0;
        boolean[] state = new boolean[m];
        for(int x = 0;x <= k;x++){
            state[removable[x]] = true;  //将搜索范围内的数据标记为真，相当于做掩码删除操作
        }

        while (i < m && j < n) {
            if (s.charAt(i) == p.charAt(j) && !state[i]) {  //掩码比较子串
                j++;
            }
            i++;
        }
        return j == n;   //只要为子串，会一直判断到子串长度，所以j==n 就是表示为子串
    }
}
