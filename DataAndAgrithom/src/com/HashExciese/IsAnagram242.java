package com.HashExciese;

public class IsAnagram242 {
    public static void main(String[] args) {
        String s= "rat";
        String t = "cat";
        System.out.println(isAnagram(s,t));
    }
    public static boolean isAnagram(String s, String t) {
        if (s.length()!=t.length())
            return false;
        int[] nums1=new int[26];  //数组位置对应26个字母
        int[] nums2=new int[26];
        for(int i=0;i<s.length();i++){
            nums1[s.charAt(i)-'a']++;
            nums2[t.charAt(i)-'a']++;
        }
        for(int i=0;i<nums1.length;i++){
            if(nums1[i]!=nums2[i]) return false;
        }
        return true;
    }
}
