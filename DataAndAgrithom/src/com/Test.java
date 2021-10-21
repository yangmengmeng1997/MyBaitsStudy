package com;

import java.util.*;

/**
 * @author ymm
 * @create 2020--12--20:09
 */

public class Test {
    public static void main(String[] args) {
        List li =new ArrayList<>(10);
        Set set = new HashSet();
        set.add(123);
        set.add(456);
        set.add("AAA");
        set.add(new String("789"));

        Iterator it =set.iterator();  //集合.迭代器
        while(it.hasNext()){
            System.out.println(it.next());
        }


    }
}
