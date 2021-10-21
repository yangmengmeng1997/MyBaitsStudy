public class Study {
    public static void main(String[] args) {
        System.out.println(fun(9,5));

    }
    public static int fun(int num,int n) {
        // n 表示目标进制, num 要转换的值
        String str= "";
        int yushu ;      // 保存余数
        int shang = num;      // 保存商
        while (shang>0) {
            yushu = shang %n;
            shang = shang/n;

            // 10-15 -> a-f
            if(yushu>9) {
                str =(char)('a'+(yushu-10)) + str;
            }else {
                str = yushu+str;
            }
        }
        //
        int sum=0;
        for(int i=0;i<str.length();i++){
             sum+=str.charAt(i)-'0';
        }
        return sum;
    }
}
