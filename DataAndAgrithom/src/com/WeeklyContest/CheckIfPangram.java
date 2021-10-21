package com.WeeklyContest;

public class CheckIfPangram {
    public static void main(String[] args){
        String sentence = "leetcode";
        System.out.println(checkIfPangram(sentence));
    }
    public static boolean checkIfPangram(String sentence) {
        int []istarget=new int[26];
        for (int i=0;i<sentence.length();i++){
            istarget[sentence.charAt(i)-'a']++;
        }
        for(int i=0;i<26;i++){
            if(istarget[i]>0) continue;
            else return false;
        }
        return true;
    }
}
