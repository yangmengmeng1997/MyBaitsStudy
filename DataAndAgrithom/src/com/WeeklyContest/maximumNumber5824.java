package com.WeeklyContest;

public class maximumNumber5824 {
    public static void main(String[] args) {
        String num = "334111";
        int[] change = {0,9,2,3,3,2,5,5,5,5};
        System.out.println(maximumNumber(num,change));
    }
    public static String maximumNumber(String num, int[] change) {
        StringBuilder str=new StringBuilder(num);
        boolean flag=false;
        for(int i=0;i<num.length();i++){
            //相等找下一个开头修改字符
            if(str.charAt(i)-'0'>=change[str.charAt(i)-'0']) continue;
            //找到最高位开始准备替换子串
            else{
                //替换子串
                for(int j=i;j<num.length();j++){
                    //这里是>，不然子串会断
                    if(str.charAt(j)-'0'>change[str.charAt(j)-'0']){
                        flag=true;
                        break;
                    }
                    //替换字符,注意用法
                    str.setCharAt(j,(char)(change[str.charAt(j)-'0']+'0'));
                    //转换为最后一个字符串后也需要结束
                    if(j==num.length()-1){
                        flag=true;
                        break;
                    }
                }
            }
            if(flag) break;
        }
        return str.toString();
    }
}
