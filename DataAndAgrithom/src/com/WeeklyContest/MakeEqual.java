package com.WeeklyContest;

import java.util.HashMap;

public class MakeEqual {
    public static void main(String[] args) {
        String[] words={"ab","a"};
        System.out.println(makeEqual(words));
    }
    public static boolean makeEqual(String[] words) {
        HashMap<Character,Integer> hashMap=new HashMap<>();
        for(int i=0;i<words.length;i++){
            for(int j=0;j<words[i].length();j++){
                char key=words[i].charAt(j);
                if(hashMap.containsKey(key)){
                    int v=hashMap.get(words[i].charAt(j));
                    v++;
                    hashMap.put(key,v);
                }else{
                    hashMap.put(key,1);
                }
            }
        }
        for(Integer v:hashMap.values()){
            if(v%words.length==0) continue;
            else return false;
        }
        return true;
    }
}
