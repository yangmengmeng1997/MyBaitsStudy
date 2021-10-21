package lettcode;

import java.util.HashSet;

public class intersection349 {
    public static void main(String[] args) {
        int[] num1={4,9,5};
        int[] num2={9,4,9,8,4};
        int[] ans=intersection(num1,num2);
        for(int a:ans){
            System.out.println(a);
        }
    }
    //暴力解的就是双重for循环了但是用hashset进行优化就不一样了
    public static int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> hashSet=new HashSet<>();
        for(int i=0;i<nums1.length;i++){
            for(int j=0;j<nums2.length;j++){
                if(nums1[i]==nums2[j]){
                    hashSet.add(nums1[i]);
                    break;
                }
            }
        }
        int[] ans=new int[hashSet.size()];
        int[] a = hashSet.stream().mapToInt(Integer::intValue).toArray();
        return a;
    }

    public static int[] intersection1(int[] nums1, int[] nums2){
        HashSet<Integer> myset1=new HashSet<>();
        HashSet<Integer> resset=new HashSet<>();
        //将1中的数据去重减少计算
        for (int i:nums1){
             myset1.add(i);
        }
        //将1中去重的数据与resset模块进行对比
        for(int i=0;i<nums2.length;i++){
            if(myset1.contains(nums2[i]))
                resset.add(nums2[i]);
        }
        int[] ans=new int[resset.size()];
        int index=0;
        for(int i:resset){   //set几种遍历方式忘光了
            ans[index++]=i;
        }
        return ans;
    }
}
