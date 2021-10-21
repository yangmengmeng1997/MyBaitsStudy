package com.Greedy;

public class LemonadeChange {
    public static void main(String[] args) {
        int[] bills={5,5,5,10,20};
        System.out.println(lemonadeChange(bills));
    }
    public static boolean lemonadeChange(int[] bills) {
        int[] changes={0,0};
        for(int i=0;i<bills.length;i++){
            if(bills[i]==5) changes[0]++;
            else if(bills[i]==10){
                changes[0]--;
                changes[1]++;
                if(changes[0]>=0 && changes[1]>=0) continue;
                else return false;
            }else {
                if(changes[1]>0){
                    changes[1]--;
                    changes[0]--;
                }else{
                    changes[0]=changes[0]-3;
                }
                if(changes[0]>=0 && changes[1]>=0) continue;
                else return false;
            }
        }
        return true;
    }
}
