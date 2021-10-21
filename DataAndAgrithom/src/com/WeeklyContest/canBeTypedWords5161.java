package com.WeeklyContest;

public class canBeTypedWords5161 {
    public static void main(String[] args) {
        System.out.println(canBeTypedWords("leet code","e"));
    }
    public static int canBeTypedWords(String text, String brokenLetters) {
        String[] temp=text.split(" ");
        int ans=0;
        for(int i=0;i<temp.length;i++){
            boolean flag=false;
            for(int j=0;j<brokenLetters.length();j++){
                if(temp[i].contains(brokenLetters.substring(j,j+1))){
                    flag=true;
                    break;
                }
            }
            if(!flag) ans++;
        }
        return ans;
    }
}
