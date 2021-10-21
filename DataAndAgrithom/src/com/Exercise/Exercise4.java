package com.Exercise;

import java.util.Scanner;

public class Exercise4 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int num=0;
        while(sc.hasNextLine()){
            num++;
            String s=sc.nextLine();
            String[] strings = s.trim().split(" ");
            int[] nums=new int[strings.length];
            for(int i=0;i<strings.length;i++){
                nums[i]= Integer.parseInt(strings[i]);
            }
            int maxmultiy=Integer.MIN_VALUE;
            int start = 0;
            int end =0;
            for(int i=0;i<nums.length;i++){
                int sum=1;
                for(int j=i;j<nums.length;j++){   //一直到结束即可
                    if(j==i) sum=nums[i];
                    else
                        sum=sum*nums[j];
                    if(sum>maxmultiy){   //
                        maxmultiy=sum;
                        end=j;   //更新end
                        start=i; //更新start
                    }
                    if(sum==maxmultiy){
                        //选择范围最小的
                        if(j-i<end-start){
                            end=j;
                            start=i;
                        }
                        if(j-i==end-start){   //范围一样
                            //取最小的起始位置
                            if(start>i){
                                start=i;
                                end=j;
                            }
                        }
                    }
                }
            }
            System.out.printf("Case %d: %d %d-%d\n",num,maxmultiy,start,end);
        }
    }
}
