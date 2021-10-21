package com.WeeklyContest;

public class numberOfRounds5789 {
    //模拟半天就这？？？？
    public static void main(String[] args) {
        String startTime="12:01";
        String finishTime="12:44";
        System.out.println(numberOfRounds1(startTime,finishTime));
    }
    public static int numberOfRounds(String startTime, String finishTime) {
        int[] minutes={0,15,30,45};
        int sum=0;
        //HH:MM
        //1.首先处理的就是正常没通宵的时间。就是startTime的HH<finishTime的HH
        int startHour=Integer.parseInt(startTime.substring(0,2));
        int startMinute=Integer.parseInt(startTime.substring(3,5));
        int finishHour=Integer.parseInt(finishTime.substring(0,2));
        int finishMinute=Integer.parseInt(finishTime.substring(3,5));
        if(startHour<finishHour){
            //不同小时区间玩游戏；
            if(startMinute>45){
                startMinute=0;
                startHour+=1;
            }
            int gamenum=(finishHour-startHour)*4;  //一小时4局
            startMinute=ValidStartTime(startMinute,minutes);
            finishMinute=ValidEndTime(finishMinute,minutes);
            int minutegamenum=(finishMinute-startMinute)/15;
            sum=gamenum+minutegamenum;
        }
        //特意加后缀条件表示没有通宵玩游戏
        else if(startHour==finishHour && startMinute<finishMinute){  //同一小时时间段玩游戏
            if(startMinute>45) sum=0;
            else{
                startMinute=ValidStartTime(startMinute,minutes);
                finishMinute=ValidEndTime(finishMinute,minutes);
                int minutegamenum=(finishMinute-startMinute)/15;
                sum=minutegamenum;
            }
        }
        else{  //通宵玩游戏
            if(startMinute>45){
                startMinute=0;
                startHour+=1;
            }
            int gamenum=(24-startHour)*4;  //一小时4局
            startMinute=ValidStartTime(startMinute,minutes);
            int tempfinishMinute=0;
            int minutegamenum=(tempfinishMinute-startMinute)/15;
            sum=gamenum+minutegamenum;
            //开始时间玩到24:00
            startHour=0;
            startMinute=0;
            finishMinute=ValidEndTime(finishMinute,minutes);
            gamenum=(finishHour-startHour)*4;
            minutegamenum=(finishMinute-startMinute)/15;
            sum=sum+gamenum+minutegamenum;
        }
        return sum;
    }
    //判断开始时间在哪一个区间
    //求出起始的有效时间
    public static int ValidStartTime(int originalTime,int[] minutes) {
        int i=0;
        for (; i < minutes.length; i++) {
            if(originalTime>minutes[i]) continue;
            else break;
        }
        return minutes[i];
    }
    //求出结束的有效时间
    public static int ValidEndTime(int originalTime,int[] minutes) {
        if (originalTime>minutes[3]) return minutes[3];
        int i=0;
        for (; i < minutes.length; i++) {
            if(originalTime>minutes[i]) continue;
            else break;
        }
        if(originalTime==minutes[i]) return minutes[i];
        return minutes[i-1];
    }

    //不是笨比模拟，而是将小时转化为分钟来直接计算
    public static int numberOfRounds1(String startTime, String finishTime){
        int startHour=Integer.parseInt(startTime.substring(0,2));
        int startMinute=Integer.parseInt(startTime.substring(3,5));
        int finishHour=Integer.parseInt(finishTime.substring(0,2));
        int finishMinute=Integer.parseInt(finishTime.substring(3,5));
        int t1=startHour*60+startMinute;
        int t2=finishHour*60+finishMinute;
        if(t2<t1){
            t2+=1440;  //表示通宵玩游戏
        }
        t2=(t2/15)*15;  //结束时间转化为标准结束时间，也就是15的整数倍
        return Math.max(0,t2-t1)/15;
    }

}
