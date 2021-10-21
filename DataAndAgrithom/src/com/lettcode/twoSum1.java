package lettcode;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.HashMap;
import java.util.Map;

public class twoSum1 {
    public static void main(String[] args) {
        int[] num={2,7,11,15};
        int target=17;
        int[] ans=twoSum(num,target);
        for(int i:ans){
            System.out.println(i);
        }
    }
    public  static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }
}
