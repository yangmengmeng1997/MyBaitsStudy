import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Map;

public class IsThree5830 {
    public static void main(String[] args) {
        System.out.println(isThree(2));
    }
    public static boolean isThree(int n) {
        if(n==2) return false;
        int sum=0;
        for(int i=2;i< n;i++){
            if(n%i==0) sum++;
        }
        return sum==1;
    }
}
