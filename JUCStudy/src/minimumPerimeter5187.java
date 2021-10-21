public class minimumPerimeter5187 {
    //还是要总结公式，不然怎么做？
    //笨比模拟会超时
    public static void main(String[] args) {
        System.out.println(minimumPerimeter(10000));
    }
    public static long minimumPerimeter(long neededApples) {
        int n=2;
        while(caluate(n++)<neededApples){
            continue;
        }
        return (n-1)*4;
    }
    //输入正方形边长返回可以种的苹果数量
    //奇数边种不了果树,每一个坐标处都有果树
    public static long caluate(long n){
        if(n%2==1) n=n-1;
        long sum=0;
        for(int i=0;i<=n/2;i++){
            for(int j=0;j<=n/2;j++){
                sum+=(i+j)*4; //划分四块
            }
        }
        for(int k=1;k<=n/2;k++)
            sum-=k*4;   //减去4个重复的轴
        return sum;
    }
}
