package com.DFSExercise;

import java.util.*;

public class LetterCombinations {
    public static void main(String[] args){
        String digits="23";
        List<String> res=letterCombinations(digits);
        System.out.println(res);
    }
    public static List<String> letterCombinations(String digits) {
        if(digits=="") return new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        String[] nums={"2","3","4","5","6","7","8","9"};
        String[] values={"abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
        for(int i=0;i<nums.length;i++){
            map.put(nums[i],values[i]);
        }  //test is ok
        List<String> res=new LinkedList<>();
        backtrack(new StringBuilder(),digits,res,0,map);
        return res;
    }
    public static void backtrack(StringBuilder combination, String nextdigit,List<String> res,int i,Map<String,String> map){
        if(i==nextdigit.length()){
            res.add(combination.toString());
            return;
        }
        else{
            //取出每一个字符
            String s=String.valueOf(nextdigit.charAt(i));
            String letters=map.get(s);
            for(int j=0;j<letters.length();j++){
                String letter=String.valueOf(letters.charAt(j));
                backtrack(new StringBuilder(combination+letter),nextdigit,res,i+1,map);
            }
        }
    }
}
