package lettcode;

import org.omg.PortableInterceptor.INACTIVE;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class threeSum15 {
    public static void main(String[] args) {
        int[] nums={-1,0,1,2,-1,-4};
        Arrays.sort(nums);
//        for(int i:nums){
//            System.out.println(i);
//        }
        System.out.println(threeSum(nums));
    }
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res=new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length-2;i++){
            if(nums[i]>0) break;    //排序之后最小的都是大于0，直接跳出
            if(i>0 && nums[i]==nums[i-1]) continue;  //去掉重复的数字，因为排序后相同数字连续的避免有重复解
            int left=i+1;      //当前指针加1是左指针
            int right=nums.length-1; //右指针为末尾
            while(left<right){   //每一个数字都有左右指针指向搜索范围，两者相等才可以算搜索结束
                int sum=nums[i]+nums[left]+nums[right];
                if(sum>0) right--;   //sum>0 说明和大了，这时只能缩减right，left移动只会增大
                else if(sum<0) left++;   //sum>0 说明和大了，left移动可以增大
                else {
                    //找到目标了
                    res.add(Arrays.asList(nums[i],nums[left],nums[right]));   //知识点不知道
                    //简单去重
                    while (left<right && nums[right]==nums[right-1]) right--;
                    while (left<right && nums[left]==nums[left+1]) left++;

                    //一个循环里找到一组目标后，继续寻找其他答案。这时如果只移动一个指针是不可能找到的
                    //因为此时已经适配目标值，移动一个只会偏大或者偏小。只有同时移动两个才可能重新找到
                    right--;
                    left++;
                }
            }
        }
        return res;
    }
}
