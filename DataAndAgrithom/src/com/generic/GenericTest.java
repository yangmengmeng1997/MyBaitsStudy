package com.generic;

import java.util.*;

/**
 * 泛型
 * @author ymm
 * @create 2020--12--19:44
 */
public class GenericTest {
    public static void main(String[] args){
        //只可以添加整数
        ArrayList<Integer> arrry=new ArrayList<>();
        arrry.add(123);
        arrry.add(456);
        arrry.add(789);

        Map<String ,Integer> map=new HashMap<>();
        map.put("xiu",12);
        map.put("xiaoran",15);
        //map.put("111","111"); 自动检测报错

        //迭代list
        Iterator it =arrry.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
//        //迭代map,这个只是遍历了key值，只用到keyset的迭代
//        Iterator mapit =map.keySet().iterator();
//        while (mapit.hasNext()){
//            System.out.println(mapit.next());
//        }

        //这是遍历，有泛型的嵌套，泛型的类型中必须是一个类
        Set<Map.Entry<String,Integer>> entry = map.entrySet();  //取集合
        //迭代器生成时要也要用泛型来指定才可对特定的进行遍历
        Iterator<Map.Entry<String ,Integer>> iterator=entry.iterator();
        while(iterator.hasNext()){
            //System.out.println(iterator.next());
            Map.Entry<String,Integer> e=  iterator.next();
            String name=e.getKey();
            int num =e.getValue();
            System.out.println("name is "+name+" and age is "+String.valueOf(num));

        }
    }




}
